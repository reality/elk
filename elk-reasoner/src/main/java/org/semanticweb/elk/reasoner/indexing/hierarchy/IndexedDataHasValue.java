/*
 * #%L
 * ELK Reasoner
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
package org.semanticweb.elk.reasoner.indexing.hierarchy;

import org.semanticweb.elk.owl.interfaces.ElkLiteral;
import org.semanticweb.elk.reasoner.datatypes.DatatypeEngine;
import org.semanticweb.elk.reasoner.datatypes.enums.Datatype;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedClassExpressionVisitor;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedDataHasValueVisitor;

public class IndexedDataHasValue extends IndexedDatatypeExpression {

	protected final ElkLiteral filler;
	protected Datatype datatype;

	protected IndexedDataHasValue(IndexedDataProperty dataProperty, ElkLiteral elkLiteral) {
		super(dataProperty);
		this.filler = elkLiteral;
		datatype = Datatype.getByIri(filler.getDatatype().getDatatypeIRI());
	}

	public ElkLiteral getFiller() {
		return filler;
	}

	@Override
	public Datatype getDatatype() {
		return datatype;
	}
	
	@Override
	protected void updateOccurrenceNumbers(int increment,
			int positiveIncrement, int negativeIncrement) {
		if (negativeOccurrenceNo == 0 && negativeIncrement > 0) {
			// first negative occurrence of this expression
			DatatypeEngine.register(property, this);
		}

		positiveOccurrenceNo += positiveIncrement;
		negativeOccurrenceNo += negativeIncrement;

		if (negativeOccurrenceNo == 0 && negativeIncrement < 0) {
			// no negative occurrences of this expression left
			DatatypeEngine.unregister(property, this);
		}
	}

	public <O> O accept(IndexedDataHasValueVisitor<O> visitor) {
		return visitor.visit(this);
	}

	@Override
	public <O> O accept(IndexedClassExpressionVisitor<O> visitor) {
		return accept((IndexedDataHasValueVisitor<O>) visitor);
	}

	@Override
	public String toString() {
		return "DataHasValue(" + '<' + this.property.getIri().getFullIriAsString()
				+ "> \"" + this.filler.getLexicalForm() + "\"^^<"
				+ this.filler.getDatatype().getIri().getFullIriAsString() + ">)";
	}
}
