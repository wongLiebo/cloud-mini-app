package com.mini.cloud.common.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UA {
	private String browser;
	private String version;
	private String engine;
	private String engineVersion;
	private String os;
	private String platform;
	private boolean isMobile;
}
