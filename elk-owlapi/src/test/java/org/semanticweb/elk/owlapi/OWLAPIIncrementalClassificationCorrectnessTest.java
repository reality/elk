/**
 * 
 */
package org.semanticweb.elk.owlapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.elk.owl.parsing.Owl2ParseException;
import org.semanticweb.elk.reasoner.ClassTaxonomyTestOutput;
import org.semanticweb.elk.reasoner.Reasoner;
import org.semanticweb.elk.reasoner.ReasoningTestManifest;
import org.semanticweb.elk.reasoner.incremental.BaseIncrementalClassificationCorrectnessTest;
import org.semanticweb.elk.reasoner.incremental.BaseIncrementalReasoningCorrectnessTest;
import org.semanticweb.elk.reasoner.incremental.OnOffVector;
import org.semanticweb.elk.reasoner.stages.PostProcessingStageExecutor;
import org.semanticweb.elk.util.collections.ArrayHashSet;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyCreationIOException;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * @author Pavel Klinov
 *
 * pavel.klinov@uni-ulm.de
 */
public class OWLAPIIncrementalClassificationCorrectnessTest extends
		BaseIncrementalClassificationCorrectnessTest<OWLAxiom> {
	
	private OWLOntology testOntology_;
	
	@SuppressWarnings("unchecked")
	final Set<AxiomType<?>> DYNAMIC_AXIOM_TYPES = new HashSet<AxiomType<?>>(Arrays.asList(AxiomType.SUBCLASS_OF, AxiomType.EQUIVALENT_CLASSES, AxiomType.DISJOINT_CLASSES));

	public OWLAPIIncrementalClassificationCorrectnessTest(
			ReasoningTestManifest<ClassTaxonomyTestOutput, ClassTaxonomyTestOutput> testManifest) {
		super(testManifest);
	}

	@Override
	protected void applyChanges(
			final Reasoner reasoner,
			final Iterable<OWLAxiom> changes,
			final BaseIncrementalReasoningCorrectnessTest.CHANGE type) {
		// the changes are applied indirectly by modifying the ontology
		OWLOntologyManager manager = testOntology_.getOWLOntologyManager();
		List<OWLOntologyChange> ontologyChanges = new ArrayList<OWLOntologyChange>();
		
		for (OWLAxiom axiom : changes) {
			switch (type) {
			case ADD:
				ontologyChanges.addAll(manager.addAxiom(testOntology_, axiom));
				break;
			case DELETE:
				ontologyChanges.addAll(manager.removeAxiom(testOntology_, axiom));
				break;
			}
		}
	}

	@Override
	protected void dumpChangeToLog(OWLAxiom change) {
		LOGGER_.trace(change.toString());
	}

	@Override
	protected void loadAxioms(InputStream stream, List<OWLAxiom> staticAxioms,
			OnOffVector<OWLAxiom> changingAxioms) throws IOException,
			Owl2ParseException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = null;
		
		try {
			ontology = manager.loadOntologyFromOntologyDocument(stream);
			
			for (OWLAxiom axiom : ontology.getLogicalAxioms()) {
				if (DYNAMIC_AXIOM_TYPES.contains(axiom.getAxiomType())) {
					changingAxioms.add(axiom);
				}
				else {
					staticAxioms.add(axiom);
				}
			}
			
		} catch (OWLOntologyCreationException e) {
			throw new Owl2ParseException(e);
		}
	}

	@Override
	protected Reasoner getReasoner(final Iterable<OWLAxiom> axioms) {
		Set<OWLAxiom> axSet = new ArrayHashSet<OWLAxiom>();
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		
		for (OWLAxiom axiom : axioms) {
			axSet.add(axiom);
		}

		try {
			testOntology_ = manager.createOntology(axSet);
		} catch (OWLOntologyCreationIOException e) {
			throw new RuntimeException(e);
		} catch (OWLOntologyCreationException e) {
			throw new RuntimeException(e);
		}
		//important to use the buffering mode here
		//otherwise we'd need to issue a query to the ElkReasoner
		//before we could use the internal reasoner directly in the tests
		ElkReasoner reasoner = new ElkReasoner(testOntology_, true,
				new PostProcessingStageExecutor());
		
		return reasoner.getInternalReasoner();
	}

}