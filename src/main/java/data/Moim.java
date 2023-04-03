package data;

import java.util.Date;

public class Moim {
	String id;
	String managerId;
	String event;
	String type;
	String cate;
	String description;
	int maxPerson;
	int currentPerson;

	Date beginDate;
	Date endDate;
	int finalCost;
	
	String ManagerName;
	String ManagerAvatarURL;
	
	

	public String getManagerName() {
		return ManagerName;
	}

	public void setManagerName(String managerName) {
		ManagerName = managerName;
	}

	public String getManagerAvatarURL() {
		return ManagerAvatarURL;
	}

	public void setManagerAvatarURL(String managerAvatarURL) {
		ManagerAvatarURL = managerAvatarURL;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCate() {
		return cate;
	}

	public void setCate(String cate) {
		this.cate = cate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMaxPerson() {
		return maxPerson;
	}

	public void setMaxPerson(int maxPerson) {
		this.maxPerson = maxPerson;
	}

	public int getCurrentPerson() {
		return currentPerson;
	}

	public void setCurrentPerson(int currentPerson) {
		this.currentPerson = currentPerson;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getFinalCost() {
		return finalCost;
	}

	public void setFinalCost(int finalCost) {
		this.finalCost = finalCost;
	}

}
