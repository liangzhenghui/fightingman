package common;

public class Response {
	private boolean error;
	private String errorType;
	private String errorMessage;
	private Object result;
	private Object result1;//备用封装字段1
	private Object result2;//备用封装字段1

	public Response() {
		super();
	}

	public Response(boolean error) {
		super();
		this.error = error;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object getResult1() {
		return result1;
	}

	public void setResult1(Object result1) {
		this.result1 = result1;
	}

	public Object getResult2() {
		return result2;
	}

	public void setResult2(Object result2) {
		this.result2 = result2;
	}

}
