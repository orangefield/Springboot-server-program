package site.metacoding.serverproject.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import site.metacoding.serverproject.domain.hospital.Hospital;
import site.metacoding.serverproject.domain.hospital.HospitalRepository;

@Controller
public class HospitalController {

    private HospitalRepository hospitalRepository;

    public HospitalController(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    // 메인페이지
    @GetMapping("/")
    public String download() {
        return "download";
    }

    // 다운로드 및 저장 기능
    @GetMapping("/data")
    public String data() {

        // 데이터 다운로드
        RestTemplate rt = new RestTemplate();

        Hospital[] response = rt.getForObject("http://3.38.254.72:5000/api/hospital?sidoCdNm=부산&sgguCdNm=부산사하구",
                Hospital[].class);
        List<Hospital> hospitals = Arrays.asList(response);

        // DB에 저장
        hospitalRepository.saveAll(hospitals);

        return "redirect:/list";
    }

    // 리스트 페이지(데이터 뿌리기)
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("hospitals", hospitalRepository.findAll());

        return "list";
    }
}
