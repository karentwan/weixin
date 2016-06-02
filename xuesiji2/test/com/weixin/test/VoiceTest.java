package com.weixin.test;

import org.junit.Test;

import com.weixin.model.Video;
import com.weixin.model.VideoMessage;
import com.weixin.model.Voice;
import com.weixin.model.VoiceMessage;
import com.weixin.util.MessageUtil;

public class VoiceTest {

//	@Test
	public void testVoice() {
		Voice v = new Voice();
		v.setMediaId("mediaId......");
		VoiceMessage vm = new VoiceMessage();
		vm.setCreateTime(1232);
		vm.setFromUserName("231");
		vm.setFuncFlag(0);
		vm.setToUserName("132");
		vm.setMsgType("voice");
		vm.setVoice(v);
		System.out.println(MessageUtil.clazzToXml(vm));
	}
	@Test
	public void testVideo() {
		Video v = new Video();
		v.setMediaId("mediaId......");
		v.setTitle("test");
		v.setDescription("test");
		VideoMessage vm = new VideoMessage();
		vm.setCreateTime(1232);
		vm.setFromUserName("231");
		vm.setFuncFlag(0);
		vm.setToUserName("132");
		vm.setMsgType("voice");
		vm.setVideo(v);
		System.out.println(MessageUtil.clazzToXml(vm));
	}
	
}
