package com.ecommerce.grutils.network

import static org.junit.Assert.*;
import org.junit.*;

import com.jcraft.jsch.JSch;

class SshCommandTest {
	SshCommand command
	
	@Before
	void setup(){
		command = new SshCommand("testhost", "testUser", "testkeyLocation")
	}
	
	@Test
	void setupLoggingForJSchAndConstructor(){
		assert command
		assert(null!=JSch.getLogger())
		println(JSch.getLogger().toString())
	}
}
