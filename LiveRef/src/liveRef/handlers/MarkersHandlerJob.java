package liveRef.handlers;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;

import liveRef.Components.Candidate;
import liveRef.Components.LiveRef;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class MarkersHandlerJob extends WorkspaceJob {
    
    private IFile fileToModify;
    private LiveRef liveRef;
    private Document doc;
    
    private final String MARKER_BASE = "LiveRef.markerC";

    public MarkersHandlerJob(String name, IFile fileToModify, LiveRef liveref) {
        super(name);
        this.fileToModify = fileToModify;
        this.liveRef = liveref;
        this.doc = new Document(ResourceManager.getFileContent());
    }
    
    private static class State{
    	public static List<IMarker> markers = new ArrayList<>();
    }

    @Override
    public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
    	deleteAllMarkers();
		int steps = liveRef.getCandidates().size()/10;
		
		int classification = 10;
    	for(int i = 0; i < liveRef.getCandidates().size(); ++i) {
    		createMarker(liveRef.getCandidates().get(i), classification);
    		if(steps == 0 || i % steps == 0) {
    			--classification;
    		}
    	}
        return Status.OK_STATUS;
    }

	private void deleteAllMarkers() throws CoreException {
		for(IMarker marker: State.markers) {
    		marker.delete();
    	}
	}

	private void createMarker(Candidate candidate, int classification) throws CoreException {
		Map<String, Object> attributes = new HashMap<>();
		
		int startLine = 0, endLine = 0;
    	try {
			startLine = doc.getLineOfOffset(candidate.range.start) + 1;
			endLine = doc.getLineOfOffset(candidate.range.end) + 1;
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

    	attributes.put(IMarker.LINE_NUMBER, startLine);
		attributes.put(IMarker.SOURCE_ID, candidate);
		attributes.put(IMarker.MESSAGE, "Extract method from lines: " + startLine + " - " + endLine);
		if(fileToModify.exists()) {
			State.markers.add(fileToModify.createMarker(MARKER_BASE + classification , attributes));
		}
	}

}

