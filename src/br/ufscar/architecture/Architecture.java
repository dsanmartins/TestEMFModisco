package br.ufscar.architecture;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.modisco.omg.kdm.code.AbstractCodeElement;
import org.eclipse.modisco.omg.kdm.structure.AbstractStructureElement;

import rMSAS.architecturalModel.ArchitecturalModelFactory;
import rMSAS.architecturalModel.ArchitecturalRefactoringModel;
import rMSAS.architecturalModel.Move;
import rMSAS.codeModel.CodeModelFactory;
import rMSAS.constraintModel.AbstractCondition;

public class Architecture {


	public ArchitecturalRefactoringModel createArchitectureModel(AbstractStructureElement abstraction, AbstractStructureElement component1, AbstractStructureElement component2, 
			AbstractCodeElement entity, AbstractCodeElement codeElement1, AbstractCodeElement codeElement2, AbstractCondition archPreCondition, AbstractCondition codePreCondition ) {

		ArchitecturalModelFactory factoryArch = ArchitecturalModelFactory.eINSTANCE;
		ArchitecturalRefactoringModel model = factoryArch.createArchitecturalRefactoringModel();
		model.setDescription("Hola Mundo");
		model.setEngine("ATL");
		model.setName("AR1");
		model.setSmell("Scattered Reference Input");


		//Add architecturalOperation
		Move archMove = factoryArch.createMove();
		archMove.setDescription("My first architectural Move operation");
		archMove.setAbstraction(abstraction);
		archMove.setFrom(component1);
		archMove.setTo(component2);
		archMove.setCondition(archPreCondition);
		model.getArchitecturalOperation().add(archMove);

		//Add codeModel to architecturalModel
		CodeModelFactory factoryCode = CodeModelFactory.eINSTANCE;
		rMSAS.codeModel.Move codeMove = factoryCode.createMove();
		codeMove.setDescription("My first code Move operation");
		codeMove.setEntity(entity);
		codeMove.setFrom(codeElement1);
		codeMove.setTo(codeElement2);
		codeMove.setCondition(codePreCondition);
		model.getArchitecturalOperation().get(0).getCodeOperation().add(codeMove);

		return model;
	}

	public void persistArchitecturalRefactoringModel(ArchitecturalRefactoringModel model, Resource resource, String modelName) {

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("architecturalmodel", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// create a resource
		URI uriMyInstance = URI.createFileURI(new File("Resource/"+ modelName + ".architecturalmodel").getAbsolutePath());
		resource = resSet.createResource(uriMyInstance);
		// Get the first model element and cast it to the right type, in my
		// example everything is hierarchical included in this first node
		resource.getContents().add(model);

		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
