/**
 * 
 */
package org.semanticweb.elk.reasoner.saturation.tracing.inferences;

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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectProperty;
import org.semanticweb.elk.reasoner.saturation.IndexedContextRoot;
import org.semanticweb.elk.reasoner.saturation.conclusions.implementation.AbstractConclusion;
import org.semanticweb.elk.reasoner.saturation.conclusions.implementation.BackwardLinkImpl;
import org.semanticweb.elk.reasoner.saturation.conclusions.implementation.ContradictionImpl;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.BackwardLink;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Contradiction;
import org.semanticweb.elk.reasoner.saturation.conclusions.visitors.ConclusionVisitor;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.visitors.InferenceVisitor;

/**
 * Represents a {@link Contradiction} produced via a propagation over a
 * {@link BackwardLink}.
 * 
 * @author Pavel Klinov
 *
 *         pavel.klinov@uni-ulm.de
 */
public class PropagatedContradiction extends AbstractConclusion implements
		Contradiction, Inference {

	private final IndexedObjectProperty premiseRelation_;

	private final IndexedContextRoot premiseSource_;

	private final IndexedContextRoot inconsistentRoot_;

	public PropagatedContradiction(BackwardLink premise,
			IndexedContextRoot inconsistentRoot) {
		premiseRelation_ = premise.getRelation();
		premiseSource_ = premise.getSource();
		inconsistentRoot_ = inconsistentRoot;
	}

	public PropagatedContradiction(IndexedObjectProperty relation,
			IndexedContextRoot sourceRoot, IndexedContextRoot inconsistentRoot) {
		premiseRelation_ = relation;
		premiseSource_ = sourceRoot;
		inconsistentRoot_ = inconsistentRoot;
	}

	@Override
	public <I, O> O accept(ConclusionVisitor<I, O> visitor, I input) {
		return visitor.visit(this, input);
	}

	@Override
	public String toString() {
		return "Propagated contradiction";
	}

	@Override
	public <I, O> O acceptTraced(InferenceVisitor<I, O> visitor, I parameter) {
		return visitor.visit(this, parameter);
	}

	public BackwardLink getLinkPremise() {
		return new BackwardLinkImpl(premiseSource_, premiseRelation_);
	}

	@SuppressWarnings("static-method")
	public Contradiction getContradictionPremise() {
		return ContradictionImpl.getInstance();
	}

	@Override
	public IndexedContextRoot getInferenceContextRoot(
			IndexedContextRoot rootWhereStored) {
		return inconsistentRoot_;
	}

}
