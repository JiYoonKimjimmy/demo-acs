package me.jimmyberg.acs.service.collector

import me.jimmyberg.acs.support.enumerate.ADSContent
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AddressCollectorServiceTest(
    @Autowired val addressCollectorService: AddressCollectorService
) {

    @DisplayName("도로명주소(한글) 연계정보를 다운로드 요청하여 데이터 출력한다.")
    @Test
    fun `getTodayAddress 함수 테스트`() {
        // given
        val content = ADSContent.JUSUKR

        // when
        val collection = addressCollectorService.collect(content = content)

        // then
        val log = collection.joinToString(separator = "", transform = this::log)
        assert(log.isNotEmpty())
    }

    private fun log(content: AddressContent): String {
        return buildString {
            this.append("\n================== START [${content.name}] ==================\n")
            this.append(content.details.joinToString(separator = "\n") { it })
            this.append("\n=================== END [${content.name}] ===================\n")
        }
    }

}