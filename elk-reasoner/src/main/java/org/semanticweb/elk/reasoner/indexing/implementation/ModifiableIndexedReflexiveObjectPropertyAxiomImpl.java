package org.semanticweb.elk.reasoner.indexing.implementation;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2014 Department of Computer Science, University of Oxford
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

import org.semanticweb.elk.reasoner.indexing.conversion.ElkUnexpectedIndexingException;
import org.semanticweb.elk.reasoner.indexing.modifiable.ModifiableIndexedObjectProperty;
import org.semanticweb.elk.reasoner.indexing.modifiable.ModifiableIndexedReflexiveObjectPropertyAxiom;
import org.semanticweb.elk.reasoner.indexing.modifiable.ModifiableOntologyIndex;
import org.semanticweb.elk.reasoner.indexing.visitors.IndexedAxiomVisitor;
import org.semanticweb.elk.reasoner.saturation.rules.contextinit.ReflexivePropertyRangesContextInitRule;

/**
 * Implements {@link ModifiableIndexedReflexiveObjectPropertyAxiom}
 * 
 * @author "Yevgeny Kazakov"
 * 
 */
class ModifiableIndexedReflexiveObjectPropertyAxiomImpl extends
		ModifiableIndexedAxiomImpl implements
		ModifiableIndexedReflexiveObjectPropertyAxiom {

	private final ModifiableIndexedObjectProperty property_;

	ModifiableIndexedReflexiveObjectPropertyAxiomImpl(
			ModifiableIndexedObjectProperty property) {
		this.property_ = property;
	}

	@Override
	public final ModifiableIndexedObjectProperty getProperty() {
		return this.property_;
	}

	@Override
	public final String toStringStructural() {
		return "ReflexiveObjectProperty(" + this.property_ + ')';
	}

	@Override
	public final boolean updateOccurrenceNumbers(ModifiableOntologyIndex index,
			int increment) {

		if (!property_.isToldReflexive() && increment > 0) {
			// first occurrence of reflexivity property
			if (!index.addReflexiveProperty(property_))
				return false;
			if (!ReflexivePropertyRangesContextInitRule.addRuleFor(this, index)) {
				// revert the changes
				if (!index.removeReflexiveProperty(property_))
					throw new ElkUnexpectedIndexingException(this);
				return false;
			}
		}

		property_.updateReflexiveOccurrenceNumber(increment);

		if (!property_.isToldReflexive() && increment < 0) {
			// no occurrence of reflexivity axiom
			if (!index.removeReflexiveProperty(property_)) {
				// revert the changes
				if (!property_.updateReflexiveOccurrenceNumber(-increment))
					throw new ElkUnexpectedIndexingException(this);
				return false;
			}
			if (!ReflexivePropertyRangesContextInitRule.removeRuleFor(this,
					index)) {
				// revert the changes
				if (!property_.updateReflexiveOccurrenceNumber(-increment))
					throw new ElkUnexpectedIndexingException(this);
				if (!index.addReflexiveProperty(property_))
					throw new ElkUnexpectedIndexingException(this);
				return false;
			}
		}
		// success!
		return true;
	}

	@Override
	public final <O> O accept(IndexedAxiomVisitor<O> visitor) {
		return visitor.visit(this);
	}

}