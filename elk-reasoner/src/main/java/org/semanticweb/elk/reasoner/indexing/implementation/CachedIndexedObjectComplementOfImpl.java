package org.semanticweb.elk.reasoner.indexing.implementation;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2013 Department of Computer Science, University of Oxford
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

import org.semanticweb.elk.reasoner.indexing.caching.CachedIndexedClassExpressionFilter;
import org.semanticweb.elk.reasoner.indexing.caching.CachedIndexedObjectComplementOf;
import org.semanticweb.elk.reasoner.indexing.modifiable.ModifiableIndexedClassExpression;
import org.semanticweb.elk.reasoner.indexing.modifiable.ModifiableOntologyIndex;
import org.semanticweb.elk.reasoner.indexing.modifiable.OccurrenceIncrement;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedClassExpressionVisitor;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedObjectComplementOfVisitor;
import org.semanticweb.elk.reasoner.saturation.rules.subsumers.ContradictionFromNegationRule;
import org.semanticweb.elk.util.logging.LogLevel;
import org.semanticweb.elk.util.logging.LoggerWrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements {@link CachedIndexedObjectComplementOf}
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
class CachedIndexedObjectComplementOfImpl
		extends
		CachedIndexedComplexClassExpressionImpl<CachedIndexedObjectComplementOf>
		implements CachedIndexedObjectComplementOf {

	// logger for events
	private static final Logger LOGGER_ = LoggerFactory
			.getLogger(CachedIndexedObjectComplementOfImpl.class);

	private final ModifiableIndexedClassExpression negated_;

	CachedIndexedObjectComplementOfImpl(ModifiableIndexedClassExpression negated) {
		super(CachedIndexedObjectComplementOf.Helper
				.structuralHashCode(negated));
		this.negated_ = negated;
	}

	@Override
	public final ModifiableIndexedClassExpression getNegated() {
		return negated_;
	}

	@Override
	public final CachedIndexedObjectComplementOf structuralEquals(Object other) {
		return CachedIndexedObjectComplementOf.Helper.structuralEquals(this,
				other);
	}

	@Override
	public final boolean updateOccurrenceNumbers(ModifiableOntologyIndex index,
			OccurrenceIncrement increment) {
		if (positiveOccurrenceNo == 0 && increment.positiveIncrement > 0) {
			// first positive occurrence of this expression
			if (!ContradictionFromNegationRule.addRulesFor(this, index))
				return false;
		}

		if (negativeOccurrenceNo == 0 && increment.negativeIncrement > 0) {
			// first negative occurrence of this expression
			if (LOGGER_.isWarnEnabled()) {
				LoggerWrap
						.log(LOGGER_,
								LogLevel.WARN,
								"reasoner.indexing.IndexedObjectComplementOf",
								"ELK does not support negative occurrences of ObjectComplementOf. Reasoning might be incomplete!");
			}
		}

		positiveOccurrenceNo += increment.positiveIncrement;
		negativeOccurrenceNo += increment.negativeIncrement;

		checkOccurrenceNumbers();

		if (positiveOccurrenceNo == 0 && increment.positiveIncrement < 0) {
			// no positive occurrences of this expression left
			if (!ContradictionFromNegationRule.removeRulesFor(this, index)) {
				// revert all changes
				positiveOccurrenceNo -= increment.positiveIncrement;
				negativeOccurrenceNo -= increment.negativeIncrement;
				return false;
			}
		}
		return true;
	}

	@Override
	public final String toStringStructural() {
		return "ObjectComplementOf(" + this.negated_ + ')';
	}

	@Override
	public final <O> O accept(IndexedObjectComplementOfVisitor<O> visitor) {
		return visitor.visit(this);
	}

	@Override
	public final <O> O accept(IndexedClassExpressionVisitor<O> visitor) {
		return accept((IndexedObjectComplementOfVisitor<O>) visitor);
	}

	@Override
	public CachedIndexedObjectComplementOf accept(
			CachedIndexedClassExpressionFilter filter) {
		return filter.filter(this);
	}

}
