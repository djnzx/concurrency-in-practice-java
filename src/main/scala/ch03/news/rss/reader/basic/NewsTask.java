package ch03.news.rss.reader.basic;

import java.util.Date;
import java.util.List;

import ch03.news.rss.buffer.NewsBuffer;
import ch03.news.rss.data.CommonInformationItem;
import ch03.news.rss.data.RSSDataCapturer;

/**
 * The Runnable object we send to the Executor
 * @author author
 *
 */
public class NewsTask implements Runnable {

	/**
	 * The name of the RSS Feed
	 */
	private String name;
	
	/**
	 * The url of the RSS feed
	 */
	private String url;
	
	/**
	 * The buffer to store the news
	 */
	private NewsBuffer buffer;
	
	/**
	 * The constructor of the class
	 * @param name Name of the news feed
	 * @param url Url of the news feed
	 * @param buffer buffer to store the news
	 */
	public NewsTask (String name, String url, NewsBuffer buffer) {
		this.name=name;
		this.url=url;
		this.buffer=buffer;
	}
	
	
	/**
	 * Read the feed and store the news into the buffer
	 */
	@Override
	public void run() {
		System.out.println(name+": Running. " + new Date());
		RSSDataCapturer capturer=new RSSDataCapturer(name);
		List<CommonInformationItem> items=capturer.load(url);
		
		for (CommonInformationItem item: items) {
			buffer.add(item);
		}
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
