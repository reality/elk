package org.semanticweb.elk.reasoner.saturation.rules.factories;

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

import org.semanticweb.elk.reasoner.indexing.hierarchy.OntologyIndex;
import org.semanticweb.elk.reasoner.saturation.IndexedContextRoot;
import org.semanticweb.elk.reasoner.saturation.SaturationStateWriter;
import org.semanticweb.elk.reasoner.saturation.SaturationStatistics;
import org.semanticweb.elk.reasoner.saturation.conclusions.implementation.ContextInitializationImpl;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Conclusion;
import org.semanticweb.elk.reasoner.saturation.conclusions.visitors.ConclusionVisitor;
import org.semanticweb.elk.reasoner.saturation.context.Context;
import org.semanticweb.elk.reasoner.saturation.rules.contextinit.ContextInitRule;
import org.semanticweb.elk.util.concurrent.computation.Interrupter;

/**
 * An {@link AbstractRuleEngine} which produces {@link Conclusion}s and
 * retrieves active {@link Context}s using the provided
 * {@link SaturationStateWriter}
 * 
 * @author "Yevgeny Kazakov"
 */
public class BasicRuleEngine extends AbstractRuleEngineWithStatistics {

	/**
	 * a {@link SaturationStateWriter} to produce new {@link Conclusion}s and
	 * query for active {@link Context}s
	 */
	private final SaturationStateWriter<?> writer_;

	/**
	 * The {@link Conclusion} used to initialize contexts using
	 * {@link ContextInitRule}s
	 */
	private final Conclusion contextInitConclusion_;

	protected BasicRuleEngine(OntologyIndex index,
			ConclusionVisitor<? super Context, Boolean> conclusionProcessor,
			WorkerLocalTodo localTodo, Interrupter interrupter,
			SaturationStateWriter<?> writer,
			SaturationStatistics aggregatedStatistics,
			SaturationStatistics localStatistics) {
		super(conclusionProcessor, localTodo, interrupter,
				aggregatedStatistics, localStatistics);
		this.writer_ = writer;
		this.contextInitConclusion_ = new ContextInitializationImpl(index);
	}

	@Override
	public void submit(IndexedContextRoot job) {
		writer_.produce(job, contextInitConclusion_);
	}

	@Override
	Context getNextActiveContext() {
		return writer_.pollForActiveContext();
	}

	protected final SaturationStateWriter<?> getWriter() {
		return writer_;
	}
}