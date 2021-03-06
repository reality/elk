/*
 * #%L
 * ELK Reasoner
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 - 2012 Department of Computer Science, University of Oxford
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
package org.semanticweb.elk.reasoner.stages;

import org.semanticweb.elk.reasoner.incremental.IncrementalChangesInitialization;
import org.semanticweb.elk.reasoner.incremental.IncrementalStages;
import org.semanticweb.elk.reasoner.saturation.SaturationStatistics;

/**
 * The base stage for initializing additions and deletions
 * 
 * @author Pavel Klinov
 * @author "Yevgeny Kazakov"
 * 
 */
abstract class AbstractIncrementalChangesInitializationStage extends
		AbstractReasonerStage {

	protected IncrementalChangesInitialization initialization = null;

	protected final SaturationStatistics stageStatistics_ = new SaturationStatistics();

	public AbstractIncrementalChangesInitializationStage(
			AbstractReasonerState reasoner, AbstractReasonerStage... preStages) {
		super(reasoner, preStages);
	}

	protected abstract IncrementalStages stage();

	@Override
	public String getName() {
		return stage().toString();
	}

	@Override
	public void executeStage() throws ElkInterruptedException {
		if (isInterrupted())
			return;
		initialization.process();
	}

	@Override
	public boolean postExecute() {
		if (!super.postExecute())
			return false;

		reasoner.ruleAndConclusionStats.add(stageStatistics_);
		stageStatistics_.reset();

		return true;
	}

	@Override
	public void printInfo() {
		// TODO
	}

	@Override
	public void setInterrupt(boolean flag) {
		super.setInterrupt(flag);
		setInterrupt(initialization, flag);
	}
}