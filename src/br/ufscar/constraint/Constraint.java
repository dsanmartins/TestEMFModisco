package br.ufscar.constraint;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import rMSAS.constraintModel.AbstractCondition;
import rMSAS.constraintModel.ConstraintModelFactory;
import rMSAS.constraintModel.PostCondition;
import rMSAS.constraintModel.PreCondition;

public class Constraint {

	public PreCondition createPrecondition(String oclQuery) {

		ConstraintModelFactory model = ConstraintModelFactory.eINSTANCE;
		// Architectural Precondition
		PreCondition precondition = model.createPreCondition();
		precondition.setOclQuery(oclQuery);
		return precondition;
	}

	public PostCondition createPostcondition(String oclQuery) {

		ConstraintModelFactory model = ConstraintModelFactory.eINSTANCE;
		// Architectural Precondition
		PostCondition condition = model.createPostCondition();
		condition.setOclQuery(oclQuery);
		return condition;
	}

	public void persistConstraintModel(AbstractCondition condition, Resource resource, String modelName ) {

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("constraintmodel", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// create a resource
		URI uriMyInstance = URI.createFileURI(new File("Resource/"+ modelName +".constraintmodel").getAbsolutePath());
		resource = resSet.createResource(uriMyInstance);
		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node
		resource.getContents().add(condition);

		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
