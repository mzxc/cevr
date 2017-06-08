package com.cevr.component.core.xml.context;

import com.cevr.component.core.xml.CkXmlReader;
import com.cevr.component.core.xml.XmlNodes;
import com.cevr.component.core.xml.XmlReader;

public class CkXmlGetter extends CkXmlReader{

	private static final long serialVersionUID = 504290881234104505L;

	@Override
	protected void refresh() throws Exception {
		XmlReader xr = (XmlReader)getCurrentContext().getAttribute(XML_ADAPTER);
		xr.loadXml();
	}
	
	public void reload() throws Exception{
		refresh();
	}
	
	public static String getXmlNodes(String tags, String sqlId) {
		XmlNodes cxn = (XmlNodes) getCurrentContext().getAttribute(CkXmlReader.XML_TAG);
		return cxn.getElementValue(tags, sqlId);
	}
}
