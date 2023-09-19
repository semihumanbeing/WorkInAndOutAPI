package com.workinandoutapi.service;

import com.workinandoutapi.entity.User;
import com.workinandoutapi.entity.UserRepository;
import com.workinandoutapi.util.AESEncryptor;
import com.workinandoutapi.util.WebControlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WebControlServiceImpl implements WebControlService {
    private final UserRepository userRepository;
    private final WebControlUtil webControlUtil;
    private final AESEncryptor encryptor = new AESEncryptor();

    @Autowired
    public WebControlServiceImpl(UserRepository userRepository, WebControlUtil webControlUtil) {
        this.userRepository = userRepository;
        this.webControlUtil = webControlUtil;
    }


    @Override
    public boolean workIn(String userId) throws Exception {
        Optional<User> oUser = userRepository.findByUserId(userId);
        User user = oUser.orElseThrow(() -> new RuntimeException("Cannot find user information"));
        String password = encryptor.decrypt(user.getPassword());
        user.setWorkIn(true);
        user.setWorkDate(new Date());
        userRepository.save(user);
        return webControlUtil.workInDaouOffice(userId, password);
    }

    @Override
    public boolean workOut(String userId) throws Exception {
        Optional<User> oUser = userRepository.findByUserId(userId);
        User user = oUser.orElseThrow(() -> new RuntimeException("Cannot find user information"));
        String password = encryptor.decrypt(user.getPassword());
        user.setWorkOut(true);
        user.setWorkDate(new Date());
        userRepository.save(user);
        return webControlUtil.workOutDaouOffice(userId, password);
    }

    @Override
    public Map<String, Boolean> getStatus(String userId) throws Exception {
        Optional<User> oUser = userRepository.findByUserId(userId);
        User user = oUser.orElseThrow(() -> new RuntimeException("Cannot find user information"));
        String password = encryptor.decrypt(user.getPassword());

        Map<String, Boolean> resMap = new HashMap<>();
        Date workDate = user.getWorkDate();

        // 오늘 날짜의 시간을 00:00:00으로 설정한 새로운 Date 객체 생성
        Calendar todayCal = Calendar.getInstance();
        todayCal.set(Calendar.HOUR_OF_DAY, 0);
        todayCal.set(Calendar.MINUTE, 0);
        todayCal.set(Calendar.SECOND, 0);
        todayCal.set(Calendar.MILLISECOND, 0);
        Date todayStart = todayCal.getTime();

        // 오늘의 날짜와 getWorkDate의 날짜를 비교
        // 날짜가 비어있으면 새로 조회


        if (workDate != null && workDate.compareTo(todayStart) >= 0) {
            // 오늘 조회 된 적 있으면 DB에 있는 내용 반환
            resMap.put("in", user.isWorkIn());
            resMap.put("out", user.isWorkOut());
            return resMap;
        } else {
            // 오늘 조회 된 적 없으면 출,퇴근 전부 활성화로 결과 전달하고 크롬드라이버 조회 결과를 DB에 저장
            resMap.put("in", false);
            resMap.put("out", false);

            // todo 확인하는 부분 비동기로
            Map<String, Boolean> workInOutMap = webControlUtil.checkWorkIn(userId, password);
            user.setWorkIn(workInOutMap.get("in"));
            user.setWorkOut(workInOutMap.get("out"));
            user.setWorkDate(new Date());
            userRepository.save(user);

            return resMap;
        }


    }
}
