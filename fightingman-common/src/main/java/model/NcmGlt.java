package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NcmGlt {
	private List dataset;
	private NcmGltParams params;

	public NcmGlt(List list, NcmGltParams ncmGltParams) {
		this.dataset = list;
		this.params = ncmGltParams;
	}
	public NcmGlt(Map map, NcmGltParams ncmGltParams) {
		List list = new ArrayList();
		list.add(map);
		this.dataset = list;
		this.params = ncmGltParams;
	}

	public List getDataset() {
		return dataset;
	}

	public void setDataset(List dataset) {
		this.dataset = dataset;
	}

	public NcmGltParams getParams() {
		return params;
	}

	public void setParams(NcmGltParams params) {
		this.params = params;
	}
}
