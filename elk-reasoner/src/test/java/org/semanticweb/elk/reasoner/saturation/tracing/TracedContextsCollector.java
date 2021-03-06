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

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.elk.reasoner.saturation.IndexedContextRoot;
import org.semanticweb.elk.reasoner.saturation.conclusions.interfaces.Conclusion;
import org.semanticweb.elk.reasoner.saturation.conclusions.visitors.AbstractConclusionVisitor;

/**
 * Collects roots of all contexts which must have been traced 
 * 
 * @author Pavel Klinov
 *
 * pavel.klinov@uni-ulm.de
 */
public class TracedContextsCollector extends AbstractConclusionVisitor<IndexedContextRoot, Boolean> {

	private final Set<IndexedContextRoot> tracedRoots_ = new HashSet<IndexedContextRoot>();
	
	@Override
	protected Boolean defaultVisit(Conclusion conclusion, IndexedContextRoot root) {
		tracedRoots_.add(conclusion.getSourceRoot(root));
		
		return true;
	}

	public Set<IndexedContextRoot> getTracedRoots() {
		return tracedRoots_;
	}
}
