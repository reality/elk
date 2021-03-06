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
package org.semanticweb.elk.reasoner.indexing.implementation;

import org.semanticweb.elk.reasoner.indexing.caching.CachedIndexedClassExpressionFilter;
import org.semanticweb.elk.reasoner.indexing.caching.CachedIndexedObjectSomeValuesFrom;
import org.semanticweb.elk.reasoner.indexing.modifiable.ModifiableIndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.modifiable.ModifiableIndexedObjectProperty;
import org.semanticweb.elk.reasoner.indexing.modifiable.ModifiableIndexedRangeFiller;
import org.semanticweb.elk.reasoner.indexing.modifiable.ModifiableOntologyIndex;
import org.semanticweb.elk.reasoner.indexing.modifiable.OccurrenceIncrement;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedClassExpressionVisitor;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedObjectSomeValuesFromVisitor;
import org.semanticweb.elk.reasoner.saturation.rules.subsumers.PropagationFromExistentialFillerRule;

/**
 * Implements {@link CachedIndexedObjectSomeValuesFrom}
 * 
 * @author "Yevgeny Kazakov"
 */
class CachedIndexedObjectSomeValuesFromImpl
		extends
		CachedIndexedComplexClassExpressionImpl<CachedIndexedObjectSomeValuesFrom>
		implements CachedIndexedObjectSomeValuesFrom {

	private final ModifiableIndexedRangeFillerImpl rangeFiller_;

	CachedIndexedObjectSomeValuesFromImpl(
			ModifiableIndexedObjectProperty property,
			ModifiableIndexedClassExpression filler) {
		super(CachedIndexedObjectSomeValuesFrom.Helper.structuralHashCode(
				property, filler));
		this.rangeFiller_ = new ModifiableIndexedRangeFillerImpl(property,
				filler);
	}

	@Override
	public final ModifiableIndexedObjectProperty getProperty() {
		return rangeFiller_.getProperty();
	}

	@Override
	public final ModifiableIndexedClassExpression getFiller() {
		return rangeFiller_.getFiller();
	}

	@Override
	public ModifiableIndexedRangeFiller getRangeFiller() {
		return rangeFiller_;
	}

	@Override
	public final CachedIndexedObjectSomeValuesFrom structuralEquals(Object other) {
		return CachedIndexedObjectSomeValuesFrom.Helper.structuralEquals(this,
				other);
	}

	@Override
	public final boolean updateOccurrenceNumbers(ModifiableOntologyIndex index,
			OccurrenceIncrement increment) {

		if (negativeOccurrenceNo == 0 && increment.negativeIncrement > 0) {
			// first negative occurrence of this expression
			if (!PropagationFromExistentialFillerRule.addRuleFor(this, index))
				return false;
		}

		negativeOccurrenceNo += increment.negativeIncrement;

		if (negativeOccurrenceNo == 0 && increment.negativeIncrement < 0) {
			// no negative occurrences of this expression left
			if (!PropagationFromExistentialFillerRule
					.removeRuleFor(this, index)) {
				// revert the changes
				negativeOccurrenceNo -= increment.negativeIncrement;
				return false;
			}
		}
		positiveOccurrenceNo += increment.positiveIncrement;
		return true;

	}

	@Override
	public final String toStringStructural() {
		return "ObjectSomeValuesFrom(" + getProperty() + ' ' + getFiller()
				+ ')';
	}

	@Override
	public final <O> O accept(IndexedObjectSomeValuesFromVisitor<O> visitor) {
		return visitor.visit(this);
	}

	@Override
	public final <O> O accept(IndexedClassExpressionVisitor<O> visitor) {
		return accept((IndexedObjectSomeValuesFromVisitor<O>) visitor);
	}

	@Override
	public CachedIndexedObjectSomeValuesFrom accept(
			CachedIndexedClassExpressionFilter filter) {
		return filter.filter(this);
	}

}
