/*
 * #%L
 * elk-reasoner
 * 
 * $Id: ElkObjectProperty.java 131 2011-07-04 00:28:11Z ykazakovgo@gmail.com $
 * $HeadURL: https://elk-reasoner.googlecode.com/svn/trunk/elk-reasoner/src/main/java/org/semanticweb/elk/syntax/ElkObjectProperty.java $
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

import org.semanticweb.elk.syntax.ElkEntityVisitor;
import org.semanticweb.elk.syntax.ElkObjectPropertyExpressionVisitor;
import org.semanticweb.elk.syntax.ElkObjectVisitor;
import org.semanticweb.elk.syntax.ElkSubObjectPropertyExpressionVisitor;
import org.semanticweb.elk.syntax.interfaces.ElkEntity;
import org.semanticweb.elk.syntax.interfaces.ElkObject;
import org.semanticweb.elk.syntax.interfaces.ElkObjectProperty;

/**
 * Corresponds to an <a href=
 * "http://www.w3.org/TR/owl2-syntax/#Object_Properties">Object Property<a> in
 * the OWL 2 specification.
 * 
 * @author Yevgeny Kazakov
 * @author Markus Kroetzsch
 */
public class ElkObjectPropertyImpl extends ElkIriObject implements ElkEntity,
		ElkObjectProperty {

	protected ElkObjectPropertyImpl(String iri) {
		super(iri);
		this.structuralHashCode = iri.hashCode();
	}

	public static ElkObjectPropertyImpl create(String objectPropertyIri) {
		return (ElkObjectPropertyImpl) factory.put(new ElkObjectPropertyImpl(
				objectPropertyIri));
	}

	public boolean structuralEquals(ElkObject object) {
		if (this == object) {
			return true;
		} else if (object instanceof ElkObjectProperty) {
			return iri.equals(((ElkObjectProperty) object).getIri());
		} else {
			return false;
		}
	}

	public <O> O accept(ElkObjectPropertyExpressionVisitor<O> visitor) {
		return visitor.visit(this);
	}

	public <O> O accept(ElkEntityVisitor<O> visitor) {
		return visitor.visit(this);
	}

	public <O> O accept(ElkSubObjectPropertyExpressionVisitor<O> visitor) {
		return visitor.visit(this);
	}

	public <O> O accept(ElkObjectVisitor<O> visitor) {
		return visitor.visit(this);
	}

}
