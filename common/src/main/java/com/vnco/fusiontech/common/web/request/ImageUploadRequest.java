package com.vnco.fusiontech.common.web.request;

import java.io.File;

public record ImageUploadRequest(String folder, File... files) {

}
