package br.ufscar.main;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmt.modisco.omg.kdm.code.ClassUnit;
import org.eclipse.gmt.modisco.omg.kdm.code.StorableUnit;
import org.eclipse.gmt.modisco.omg.kdm.kdm.KdmPackage;
import org.eclipse.gmt.modisco.omg.kdm.structure.Component;
import org.eclipse.ocl.ParserException;

import br.ufacar.architecture.Architecture;
import br.ufscar.constraint.Constraint;
import br.ufscar.query.OCLQuery;
import rMSAS.architecturalModel.ArchitecturalRefactoringModel;
import rMSAS.constraintModel.PreCondition;


public class Main {

	public static void main(String[] args) throws IOException, ParserException {
		// TODO Auto-generated method stub

		////////////////////////////////////////////////////////////////////////////////////////////////
		/*
		 * ****************************
		 * Loading KDM model from file
		 * ****************************
		 */

		URI uri = URI.createFileURI("UNDERSEA_Controller_KDM.xmi");
		KdmPackage.eINSTANCE.eClass();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,new XMIResourceFactoryImpl());
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(uri);
		resource.load(null);

		/*
		 * ****************************
		 * END Load KDM model from file
		 * ****************************
		 */
		////////////////////////////////////////////////////////////////////////////////////////////////
		/*
		 * ****************************
		 * Querying KDM Resource
		 * ****************************
		 */
		OCLQuery oclQuery = new OCLQuery();
		Component abstraction = oclQuery.getComponent(resourceSet, resource, "Reference Input_1");
		Component component1 = oclQuery.getComponent(resourceSet, resource, "Monitor_1");
		Component component2 = oclQuery.getComponent(resourceSet, resource, "Knowledge_1");

		StorableUnit entity = oclQuery.getAttribute(resourceSet, resource, "speed");
		ClassUnit codeElement1 = oclQuery.getClass(resourceSet, resource, "Monitor");
		ClassUnit codeElement2 = oclQuery.getClass(resourceSet, resource, "Knowledge");
		
		/*
		 * ****************************
		 * END Querying KDM Resource
		 * ****************************
		 */
		
		//Create architectural precondition 
		Constraint constraint = new Constraint();
		PreCondition precondition = constraint.createPrecondition("Architectural OCL query");
		constraint.persistConstraintModel(precondition, resource, "Constraint1");
		
		//Create code precondition
		constraint = new Constraint();
		precondition = constraint.createPrecondition("Code OCL query");
		constraint.persistConstraintModel(precondition, resource, "Constraint2");
		
		//Load constraints resource
		URI uriConstraint1 = URI.createFileURI("Constraint1.constraintmodel");
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,new XMIResourceFactoryImpl());
		ResourceSet resourceSetConstraint1 = new ResourceSetImpl();
		Resource resourceConstraint1 = resourceSetConstraint1.createResource(uriConstraint1);
		resourceConstraint1.load(null);
		PreCondition archPreCondition = oclQuery.getPreCondition(resourceSetConstraint1, resourceConstraint1);
		
		URI uriConstraint2 = URI.createFileURI("Constraint2.constraintmodel");
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,new XMIResourceFactoryImpl());
		ResourceSet resourceSetConstraint2 = new ResourceSetImpl();
		Resource resourceConstraint2 = resourceSetConstraint2.createResource(uriConstraint2);
		resourceConstraint2.load(null);
		PreCondition codePreCondition = oclQuery.getPreCondition(resourceSetConstraint2, resourceConstraint2);
		
		
		
		// Create architectural model
		Architecture architecture = new Architecture();
		ArchitecturalRefactoringModel archModel = architecture.createArchitectureModel(abstraction, component1, component2, 
				entity , codeElement1, codeElement2,
				archPreCondition,codePreCondition);
		
		architecture.persistArchitecturalRefactoringModel(archModel, resource, "ArchitecturalModel");
		
	}

}
