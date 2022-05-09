package com.edu.service;

import javax.inject.Inject;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.edu.dao.MypageDao;
import com.edu.dao.memberDao;
import com.edu.vo.EmailDTO;
import com.edu.vo.FileUploadVO;
import com.edu.vo.MemberVO;

@Service
public class MypageServiceImp implements MypageService{
	//git 넘기기용
	@Autowired
	private memberDao dao;
	
	@Autowired
	private MypageDao mdao;
	
	//프로필 정보가져오기
	@Override
	public MemberVO selectOne(MemberVO vo) {
		return dao.selectOne(vo);
	}

	//프로필 사진 업데이트
	@Override
	public void update_photo(FileUploadVO vo) {
		mdao.update_photo(vo);
	}

	//프로필 정보 업데이트
	@Override
	public int update_profile(MemberVO vo) {
		return mdao.update_profile(vo);
	}
	
	//아이디 찾기
	@Override
	public String findId(MemberVO vo) {
		return mdao.findId(vo);
	}

	//비밀번호 찾기
	@Inject
    JavaMailSender mailSender; // root-context.xml에 설정한 bean, 의존성을 주입
 
    @Override
    public void sendMail(EmailDTO dto) {
        try {
            // 이메일 객체
            MimeMessage msg = mailSender.createMimeMessage();
 
            // 받는 사람을 설정 (수신자, 받는사람의 이메일 주소 객체를 생성해서 수신자 이메일주소를 담음)
            msg.addRecipient(RecipientType.TO, new InternetAddress(dto.getReceiveMail()));
 
            /*
             * createMimeMessage() : MimeMessage객체를 생성시킴 (이것을 이용해서 메시지를 구성한 뒤 메일 발송)
             * addRecipient() : 메시지의 발신자를 설정 InternetAddress() : 이메일 주소 getReceiveMail() :
             * 수신자 이메일 주소
             */
 
            // 보내는 사람(이메일주소+이름)
            // (발신자, 보내는 사람의 이메일 주소와 이름을 담음)
            // 이메일 발신자
            msg.addFrom(new InternetAddress[] { new InternetAddress(dto.getSenderMail(), dto.getSenderName()) });
 
            // 이메일 제목 (인코딩을 해야 한글이 깨지지 않음)
            msg.setSubject(dto.getSubject(), "utf-8");
            // 이메일 본문 (인코딩을 해야 한글이 깨지지 않음)
            msg.setText(dto.getMessage(), "utf-8");
 
//	            html로 보낼 경우            
//	            MimeMessage message = mailSender.createMimeMessage();
//	            MimeMessageHelper helper 
//	            = new MimeMessageHelper(message, true);
//	            helper.setTo("test@host.com");
//	            helper.setText("<html><body><img src='cid:identifier1234'></body></html>", true);
 
            // 이메일 보내기
            mailSender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	
}
