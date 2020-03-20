package am.petstore.petstore.user.model

class UploadFileResponse(var fileName: String, var fileDownloadUri: String, var fileType: String, var size: Long) {

    override fun toString(): String {
        return "UploadFileResponse{" +
                "fileName='" + fileName + '\'' +
                ", fileDownloadUri='" + fileDownloadUri + '\'' +
                ", fileType='" + fileType + '\'' +
                ", size=" + size +
                '}'
    }

}