package webserver.handler;

import java.util.HashMap;
import java.util.Map;

public class ModelView {

    private final Map<String, Object> model;
    
    private String view;
    
    public ModelView() {
    	this(null);
    }
    
    public ModelView(String view) {
        this.view = view;
        this.model = new HashMap<>();
    }


    public ModelView addObject(String attributeName, Object attributeValue) {
        this.model.put(attributeName, attributeValue);
        return this;
    }

    public ModelView addAllObject(Map<String, ?> attributes) {
        this.model.putAll(attributes);
        return this;
    }

    public String getView() {
        return view;
    }

    public Map<String, Object> getModel() {
        return model;
    }

	public void setView(String view) {
		this.view = view;
	}
}
