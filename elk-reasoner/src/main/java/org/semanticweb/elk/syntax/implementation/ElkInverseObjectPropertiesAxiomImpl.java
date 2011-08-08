/*
 * #%L
 * elk-reasoner
 * 
 * $Id: ElkInverseObjectPropertiesAxiom.java 68 2011-06-04 21:49:01Z mak@aifb.uni-karlsruhe.de $
 * $HeadURL: https://elk-reasoner.googlecode.com/svn/trunk/elk-reasoner/src/main/java/org/semanticweb/elk/syntax/ElkInverseObjectPropertiesAxiom.java $
 * %%
 * Copyright (C) 2011 Oxford University Computing Laboratory
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
/**
 * @author Yevgeny Kazakov, Apr 8, 2011
 */
package org.semanticweb.elk.syntax.implementation;

import org.semanticweb.elk.syntax.ElkAxiomVisitor;
import org.semanticweb.elk.syntax.ElkObjectPropertyAxiomVisitor;
import org.semanticweb.elk.syntax.ElkObjectVisitor;
import org.semanticweb.elk.syntax.interfaces.ElkInverseObjectPropertiesAxiom;
import org.semanticweb.elk.syntax.interfaces.ElkObject;
import org.semanticweb.elk.syntax.interfaces.ElkObjectPropertyExpression;
import org.semanticweb.elk.util.HashGenerator;

/**
 * Corresponds to an <a href=
 * "http://www.w3.org/TR/owl2-syntax/#Inverse_Object_Properties_2">Inverse
 * Object Properties Axiom<a> in the OWL 2 specification.
 * 
 * @author Markus Kroetzsch
 * 
 */
public class ElkInverseObjectPropertiesAxiomImpl extends ElkObjectImpl
		implements ElkInverseObjectPropertiesAxiom {

	protected final ElkObjectPropertyExpression firstObjectPropertyExpression;
	protected final ElkObjectPropertyExpression secondObjectPropertyExpression;

	private static final int constructorHash_ = "ElkSubObjectPropertyOfAxiom"
			.hashCode();

	private ElkInverseObjectPropertiesAxiomImpl(
			ElkObjectPropertyExpression firstObjectPropertyExpression,
			ElkObjectPropertyExpression secondObjectPropertyExpression) {
		this.firstObjectPropertyExpression = firstObjectPropertyExpression;
		this.secondObjectPropertyExpression = secondObjectPropertyExpression;
		this.structuralHashCode = HashGenerator.combineListHash(
				constructorHash_,
				firstObjectPropertyExpression.structuralHashCode(),
				secondObjectPropertyExpression.structuralHashCode());
	}

	public ElkObjectPropertyExpression getFirstObjectPropertyExpression() {
		return firstObjectPropertyExpression;
	}

	public ElkObjectPropertyExpression getSecondObjectPropertyExpression() {
		return secondObjectPropertyExpression;
	}

	public static ElkInverseObjectPropertiesAxiomImpl create(
			ElkObjectPropertyExpression firstObjectPropertyExpression,
			ElkObjectPropertyExpression secondObjectPropertyExpression) {
		return (ElkInverseObjectPropertiesAxiomImpl) factory
				.put(new ElkInverseObjectPropertiesAxiomImpl(
						firstObjectPropertyExpression,
						secondObjectPropertyExpression));
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("InverseObjectProperties(");
		result.append(firstObjectPropertyExpression.toString());
		result.append(" ");
		result.append(secondObjectPropertyExpression.toString());
		result.append(")");
		return result.toString();
	}

	public boolean structuralEquals(ElkObject object) {
		if (this == object) {
			return true;
		} else if (object instanceof ElkInverseObjectPropertiesAxiomImpl) {
			return firstObjectPropertyExpression
					.equals(((ElkInverseObjectPropertiesAxiomImpl) object)
							.getFirstObjectPropertyExpression())
					&& secondObjectPropertyExpression
							.equals(((ElkInverseObjectPropertiesAxiomImpl) object)
									.getSecondObjectPropertyExpression());
		} else {
			return false;
		}
	}

	public <O> O accept(ElkObjectPropertyAxiomVisitor<O> visitor) {
		return visitor.visit(this);
	}

	public <O> O accept(ElkAxiomVisitor<O> visitor) {
		return visitor.visit(this);
	}

	public <O> O accept(ElkObjectVisitor<O> visitor) {
		return visitor.visit(this);
	}

}
