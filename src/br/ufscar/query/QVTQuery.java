package br.ufscar.query;

import java.io.File;
import java.io.OutputStreamWriter;
import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;
import org.eclipse.m2m.qvt.oml.util.Log;
import org.eclipse.m2m.qvt.oml.util.WriterLog;


public class QVTQuery {

	public List<EObject> returnComponent(Resource resource) {
		
		List<EObject> out = null; 
		
		ExecutionContextImpl context = new ExecutionContextImpl();
		OutputStreamWriter outStream = new OutputStreamWriter(System.out);
		Log log = new WriterLog(outStream);
		context.setLog(log);
		
		URI transformationURI = URI.createFileURI(new File("QVT/queries.qvto").getAbsolutePath());
		TransformationExecutor executor = new TransformationExecutor(transformationURI);
		EList<EObject> inObjects = resource.getContents();
		ModelExtent input = new BasicModelExtent(inObjects);
		ModelExtent output = new BasicModelExtent();
		
		
		context.setConfigProperty("componentName", "Knowledge_1");
		ExecutionDiagnostic result = executor.execute(context, input, output);
		if(result.getSeverity() == Diagnostic.OK) 
		{
			out = output.getContents();
			System.out.println(out.size());

		}else
			System.out.println(result.getSeverity());
		return out;
	}
}
