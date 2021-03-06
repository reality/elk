/**
 * 
 */
package org.semanticweb.elk.reasoner.saturation.tracing;

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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;
import org.semanticweb.elk.reasoner.saturation.IndexedContextRoot;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Conclusion;
import org.semanticweb.elk.reasoner.saturation.conclusions.visitors.ConclusionVisitor;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.Inference;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.visitors.AbstractInferenceVisitor;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.visitors.InferenceVisitor;
import org.semanticweb.elk.reasoner.saturation.tracing.inferences.visitors.PremiseVisitor;
import org.semanticweb.elk.util.collections.Pair;

/**
 * Recursively visits all conclusions which were used to produce a given
 * conclusion.
 * 
 * Works similarly to {@link TestTraceUnwinder} but is simpler. It does not know
 * anything about which contexts are traced or how inferences are read. It uses
 * the given {@link TraceStore.Reader} as an oracle providing access to
 * inferences.
 * 
 * TODO concurrently request and process inferences from the trace reader.
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class RecursiveTraceUnwinder implements TraceUnwinder {

	private final TraceStore.Reader traceReader_;

	private final static InferenceVisitor<IndexedContextRoot, ?> DUMMY_INFERENCE_VISITOR = new AbstractInferenceVisitor<IndexedContextRoot, Void>() {

		@Override
		protected Void defaultTracedVisit(Inference conclusion,
				IndexedContextRoot input) {
			return null;
		}

	};

	public RecursiveTraceUnwinder(TraceStore.Reader reader) {
		traceReader_ = reader;
	}

	public void accept(IndexedClassExpression context,
			final Conclusion conclusion,
			final ConclusionVisitor<IndexedContextRoot, ?> premiseVisitor) {
		accept(context, conclusion, premiseVisitor, DUMMY_INFERENCE_VISITOR);
	}

	/**
	 * 
	 * @param context
	 * @param conclusion
	 * @param conclusionVisitor
	 *            Visitor over all conclusions which were used as premises
	 * @param inferenceVisitor
	 *            Visitor over all
	 */
	@Override
	public void accept(
			IndexedContextRoot context,
			final Conclusion conclusion,
			final ConclusionVisitor<IndexedContextRoot, ?> conclusionVisitor,
			final InferenceVisitor<IndexedContextRoot, ?> inferenceVisitor) {
		final TraceUnwindingState unwindingState = new TraceUnwindingState();

		unwindingState.addToUnwindingQueue(conclusion, context);

		for (;;) {
			Pair<Conclusion, IndexedContextRoot> next = unwindingState
					.pollFromUnwindingQueue();

			if (next == null) {
				break;
			}

			unwind(next.getFirst(), next.getSecond(), unwindingState,
					inferenceVisitor);
		}
	}

	private void unwind(Conclusion conclusion,
			final IndexedContextRoot rootWhereStored,
			final TraceUnwindingState unwindingState,
			final InferenceVisitor<IndexedContextRoot, ?> inferenceVisitor) {

		final PremiseVisitor<IndexedContextRoot, ?> premiseVisitor = new PremiseVisitor<IndexedContextRoot, Void>() {

			@Override
			protected Void defaultVisit(Conclusion premise,
					IndexedContextRoot inferenceContext) {
				unwindingState.addToUnwindingQueue(premise, inferenceContext);
				return null;
			}
		};

		traceReader_.accept(rootWhereStored, conclusion,
				new AbstractInferenceVisitor<Void, Void>() {

					@Override
					protected Void defaultTracedVisit(Inference inference,
							Void v) {
						if (unwindingState.addToProcessed(inference)) {
							IndexedContextRoot inferenceContextRoot = inference
									.getInferenceContextRoot(rootWhereStored);
							// visit the premises so they can be put into the
							// queue
							inference.acceptTraced(premiseVisitor,
									inferenceContextRoot);
							// for the calling code
							inference.acceptTraced(inferenceVisitor,
									inferenceContextRoot);
						}

						return null;
					}

				});
	}

}
