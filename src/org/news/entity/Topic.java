package org.news.entity;

import java.io.Serializable;

/*
 * 主题 实体类
 * @author
 */
public class Topic implements Serializable {
    private static final long serialVersionUID = 2786883833042050721L;
	//主题id
    private int tid;
    //主题名称
	private String tname;

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

}
