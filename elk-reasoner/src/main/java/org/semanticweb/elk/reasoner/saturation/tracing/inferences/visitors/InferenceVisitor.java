/**
 * 
 */
package org.semanticweb.elk.reasoner.saturation.tracing.inferences.visitors;

import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ComposedBackwardLink;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ComposedConjunction;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ComposedForwardLink;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ContradictionFromDisjointSubsumers;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ContradictionFromInconsistentDisjointnessAxiom;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ContradictionFromNegation;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ContradictionFromOwlNothing;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.DecomposedConjunction;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.DecomposedExistentialBackwardLink;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.DecomposedExistentialForwardLink;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.DisjointSubsumerFromSubsumer;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.DisjunctionComposition;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.Inference;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.InitializationSubsumer;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.PropagatedContradiction;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.PropagatedSubsumer;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ReflexiveSubsumer;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.ReversedForwardLink;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.SubClassOfSubsumer;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.TracedPropagation;

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

/**
 * A visitor over the {@link Inference} hierarchy.
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public interface InferenceVisitor<I, O> {

	public O visit(InitializationSubsumer<?> conclusion, I parameter);

	public O visit(SubClassOfSubsumer<?> conclusion, I parameter);

	public O visit(ComposedConjunction conclusion, I input);

	public O visit(DecomposedConjunction conclusion, I input);

	public O visit(PropagatedSubsumer conclusion, I input);

	public O visit(ReflexiveSubsumer<?> conclusion, I input);

	public O visit(ComposedBackwardLink conclusion, I input);

	public O visit(ComposedForwardLink conclusion, I input);

	public O visit(ReversedForwardLink conclusion, I input);

	public O visit(DecomposedExistentialBackwardLink conclusion, I input);

	public O visit(DecomposedExistentialForwardLink conclusion, I input);

	public O visit(TracedPropagation conclusion, I input);
	
	public O visit(ContradictionFromInconsistentDisjointnessAxiom conclusion, I input);
	
	public O visit(ContradictionFromDisjointSubsumers conclusion, I input);
	
	public O visit(ContradictionFromNegation conclusion, I input);
	
	public O visit(ContradictionFromOwlNothing conclusion, I input);
	
	public O visit(PropagatedContradiction conclusion, I input);
	
	public O visit(DisjointSubsumerFromSubsumer conclusion, I input);
	
	public O visit(DisjunctionComposition conclusion, I input);

	public static final InferenceVisitor<?, ?> DUMMY = new AbstractInferenceVisitor<Void, Void>() {

		@Override
		protected Void defaultTracedVisit(Inference conclusion, Void input) {
			return null;
		}
	};
}
