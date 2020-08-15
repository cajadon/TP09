package com.tinpatiti.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadResources {

	private static final Logger LOGGER = LogManager.getLogger(ReadResources.class);

	Document domDocument;
	Element element;
	Map<String, String> elementXml;

	public Document parseXMl(String fileName) {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			domDocument = builder.parse(getFilePath(fileName));

		} catch (Exception e) {

			e.printStackTrace();
		}
		return domDocument;
	}

	public Map<String, String> getValuesFromXml(String fileName, String tagName) {

		domDocument = parseXMl(fileName);
		elementXml = new HashMap<String, String>();
		NodeList nodeList = domDocument.getElementsByTagName(tagName);

		for (int j = 0; j < nodeList.item(0).getChildNodes().getLength(); j++) {

			if (j % 2 != 0) {

				elementXml.put(nodeList.item(0).getChildNodes().item(j).getNodeName(),
						nodeList.item(0).getChildNodes().item(j).getTextContent());
			}
		}

		return elementXml;
	}

	public String getFilePath(String sFilepath) {
		String sPath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + sFilepath;
		sPath = sPath.replace('\\', '/');

		File file = new File(sPath);
		if (!file.exists()) {
			LOGGER.error("File with the Path [" + sPath + "] not Found");
		}

		return sPath;
	}

	public String createFolder(String path, long date, String vals) throws IOException {
		File files = new File(path);
		String filename = null;
		if (!files.isDirectory()) {
			if (files.mkdirs()) {
				LOGGER.debug("Multiple directories are created!");
				LOGGER.info("Multiple directories are created!");
			}
		} else {
			LOGGER.debug("Multiple directories are already present in system");
			LOGGER.info("Multiple directories are already present in system!");
		}

		File file = new File(files, "MassCEC" + date + ".xml");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(vals);
		bw.close();
		fw.close();
		filename = file.getAbsolutePath();
		LOGGER.info("result files are created!");

		return filename;
	}
}
