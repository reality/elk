/*
 * #%L
 * ELK OWL API Binding
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Department of Computer Science, University of Oxford
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
package org.semanticweb.elk.owlapi.wrapper;

import org.semanticweb.elk.owl.interfaces.ElkIndividual;
import org.semanticweb.elk.owl.interfaces.ElkNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.elk.owl.interfaces.ElkObjectPropertyExpression;
import org.semanticweb.elk.owl.visitors.ElkAssertionAxiomVisitor;
import org.semanticweb.elk.owlapi.converter.ElkIndividualConverterVisitor;
import org.semanticweb.elk.owlapi.converter.ElkObjectPropertyExpressionConverterVisitor;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;

/**
 * Implements the {@link ElkNegativeObjectPropertyAssertionAxiom} interface by wrapping instances of
 * {@link OWLNegativeObjectPropertyAssertionAxiom}
 * 
 * @author Yevgeny Kazakov
 * 
 */
public class ElkNegativeObjectPropertyAssertionAxiomWrap<T extends OWLNegativeObjectPropertyAssertionAxiom>
		extends ElkAssertionAxiomWrap<T> implements
		ElkNegativeObjectPropertyAssertionAxiom {

	public ElkNegativeObjectPropertyAssertionAxiomWrap(
			T owlNegativeObjectPropertyAssertionAxiom) {
		super(owlNegativeObjectPropertyAssertionAxiom);
	}

	public ElkIndividual getFirstIndividual() {
		ElkIndividualConverterVisitor converter = ElkIndividualConverterVisitor
				.getInstance();
		return this.owlObject.getSubject().accept(converter);
	}

	public ElkIndividual getSecondIndividual() {
		ElkIndividualConverterVisitor converter = ElkIndividualConverterVisitor
				.getInstance();
		return this.owlObject.getObject().accept(converter);
	}

	public ElkObjectPropertyExpression getObjectPropertyExpression() {
		ElkObjectPropertyExpressionConverterVisitor converter = ElkObjectPropertyExpressionConverterVisitor
				.getInstance();
		return this.owlObject.getProperty().accept(converter);
	}

	@Override
	public <O> O accept(ElkAssertionAxiomVisitor<O> visitor) {
		return visitor.visit(this);
	}

}
