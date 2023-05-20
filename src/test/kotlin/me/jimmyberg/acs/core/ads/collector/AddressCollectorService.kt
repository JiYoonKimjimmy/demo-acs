package me.jimmyberg.acs.core.ads.collector

import me.jimmyberg.acs.core.ads.client.ADSClientService
import me.jimmyberg.acs.core.ads.util.FileManagementUtil
import me.jimmyberg.acs.core.ads.util.today
import org.springframework.stereotype.Service

@Service
class AddressCollectorService(
    val adsClientService: ADSClientService
) {

    /**
     * 현재 일자 변동 주소 정보 수집 처리
     */
    fun getTodayAddress(content: String) {
        // 주소 연계 정보 조회 from ADS
        receiveFile(content)
        // 조회 파일 압축 해제
        unzipFile(content)
        // 조회 파일 읽어오기
        val items = readFile(content)
        // 조회 파일 처리
        items.forEach(::println)
    }

    fun receiveFile(content: String) {
        adsClientService
            .apply {
                dateType = "D"
                retry = "Y"
            }
            .receive(content, today())
    }

    fun unzipFile(content: String) {
        val filePath = "/files/ADS_$content/${today()}"
        val fileName = "AlterD.JUSUKR.${today()}.ZIP"
        val sourcePath = "$filePath/$fileName"

        FileManagementUtil.unzip(source = sourcePath, target = filePath)
    }

    fun readFile(content: String): List<String> {
        val path = "/files/ADS_$content/${today()}"
        val fileName = "AlterD.JUSUKR.${today()}.TH_SGCO_RNADR_LNBR.TXT"

        return FileManagementUtil.readFile(path = path, fileName = fileName)
    }

}