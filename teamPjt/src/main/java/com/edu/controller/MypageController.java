package com.edu.controller;



import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.edu.service.MypageService;
import com.edu.service.fundingMainService;
import com.edu.vo.FileUploadVO;
import com.edu.vo.FundingCommunityVO;
import com.edu.vo.FundingInfoDetailVO;
import com.edu.vo.FundingMainVO;
import com.edu.vo.Funding_optionVO;
import com.edu.vo.Funding_order_optionVO;
import com.edu.vo.MemberVO;
import com.edu.vo.StoreInfoDetailVO;
import com.edu.vo.StoreVO;
import com.edu.vo.ZzimVO;
/*version15*/ 
@Controller
@RequestMapping(value = "/mypage")
public class MypageController {
	@Autowired
	private MypageService mypageService;

	@Autowired
	private fundingMainService fms;
	
	@Autowired 
	private fundingMainService fundingMainServiece;
	
	//mesage
	@RequestMapping(value = "/message.do")
	public String message(Model model) {
		//model은 나중에 메시지오면 알림 보여주는데 쓸거임
		return "message";
	}
	
	// 소비자 마이페이지
	@RequestMapping(value = "/mypage.do")
	public String mypage(Model model, HttpServletRequest request) {
		// 세션에 있는 사용자의 정보 가져옴
		HttpSession session = request.getSession();
		MemberVO login = (MemberVO) session.getAttribute("login");
		MemberVO member = mypageService.selectOne(login);
		model.addAttribute("member", member);
		
		//펀딩리스트 3개씩 & 펀딩 개수
		//s3f에  member_idx에 따른 funding_order_idx도 가져올 수 있어야함
		List<FundingInfoDetailVO> s3f = mypageService.select3Funding(login.getMember_idx());
		
		model.addAttribute("select3Funding",s3f);
		model.addAttribute("countFunding",mypageService.countFunding(login.getMember_idx()));
		
		//스토어리스트 3개씩 & 스토어 개수
		List<StoreInfoDetailVO> s3s = mypageService.select3Store(login.getMember_idx());
		model.addAttribute("select3Store",s3s);
		model.addAttribute("countStore",mypageService.countStore(login.getMember_idx()));
		
		//찜리스트 3개씩 & 찜 개수
		List<ZzimVO> s3z = mypageService.select3Zzim(login.getMember_idx());
		model.addAttribute("select3Zzim",s3z);
		model.addAttribute("countZzim",mypageService.countZzim(login.getMember_idx()));
		
		return "mypage/mypage";
	}

	// 판매자 마이페이지
	@RequestMapping(value = "/mypage2.do")
	public String mypage2(Model model, HttpServletRequest request) {
		// 세션에 있는 사용자의 정보 가져옴
		HttpSession session = request.getSession();
		MemberVO login = (MemberVO) session.getAttribute("login");
		MemberVO member = mypageService.selectOne(login);
		model.addAttribute("member", member);

		//판매자 펀딩 내역 가져오기
		List<FundingMainVO> sfl = mypageService.sellerFundingList(login.getMember_idx());
		model.addAttribute("sellerFundingList",sfl);
		
		//판매자 스토어 내역 가져오기
		List<StoreVO> ssl = mypageService.sellerStoreList(login.getMember_idx());
		model.addAttribute("sellerStoreList", ssl);
		
		return "mypage/mypage2";
	}

	/* 펀딩 등록 메소드 */
	@RequestMapping(value = "/funding_register.do", method = RequestMethod.GET)
	public String funding_register() {
		//git 넘기기용
		return "mypage/funding_register";
	}
	
	/* 펀등 등록 - db에 모든 내용 쓰기*/
	   @RequestMapping(value = "/funding_register.do", method = RequestMethod.POST)
	   public void funding_register2(FundingMainVO vo, Model model,HttpServletRequest request
	         ,HttpServletResponse response
	         ,String funding_option_name, int []funding_option_price, String funding_option_detail, 
	         int []funding_option_stock) throws IOException {
	      
	         String []strName = funding_option_name.split(",");
	         String []strDetail = funding_option_detail.split(",");
	      
	         List<Funding_optionVO> optionVo = new ArrayList<Funding_optionVO>();
	      
	         System.out.println(vo.toString());	 
	         
	         String path = request.getSession().getServletContext().getRealPath("/resources/upload/funding");
	         
	         
	         String thum = vo.getFunding_thumbnail();
	         String content = vo.getFunding_content();
	         String notice = vo.getFunding_notice();
	         
	         
	         File dir1 = new File(path+"/"+thum);
	         File dir2 = new File(path+"/"+content);
	         File dir3 = new File(path+"/"+notice);
	         
	 		 String extension1 = thum.substring(thum.lastIndexOf("."));
	 		 String extension2 = content.substring(content.lastIndexOf("."));
	 		 String extension3 = notice.substring(notice.lastIndexOf("."));
	 		 
	 		 
	 		 String savedThumName = UUID.randomUUID() + extension1; //저장될 파일 명
	 		 String savedConName = UUID.randomUUID() + extension2; //저장될 파일 명
	 		 String savedNotName = UUID.randomUUID() + extension3; //저장될 파일 명
	         
	 		 File newdir4 = new File(path+"/"+savedThumName);
	         File newdir5 = new File(path+"/"+savedConName);
	         File newdir6 = new File(path+"/"+savedNotName);
	 		 
	 		 /*파일 이름 난수로 변경*/
		 		dir1.renameTo(newdir4);
		 		dir2.renameTo(newdir5);
		 		dir3.renameTo(newdir6);
		        
	         
	         vo.setFunding_thumbnail(savedThumName);
	         vo.setFunding_content(savedConName);
	         vo.setFunding_notice(savedNotName);
	         
	         int result = fundingMainServiece.fun_reg(vo);
	         
	         
	         response.setContentType("text/html; charset=euc-kr;");
	         PrintWriter pw = response.getWriter();
	      

	      if (result > 0) { // 회원가입 성공 -> home으로 이동
	         
	         //마지막 증가된 funding_idx 가져옴
	         int fid = vo.getFunding_idx();
	         
	         for(int i=0; i<funding_option_price.length; i++) {
	            Funding_optionVO voo = new Funding_optionVO();
	            voo.setFunding_option_name(strName[i]);
	            voo.setFunding_option_price(funding_option_price[i]);
	            voo.setFunding_option_detail(strDetail[i]);
	            voo.setFunding_option_stock(funding_option_stock[i]);
	            voo.setFunding_idx(fid);
	            optionVo.add(voo);
	         }
	         
	      
	         int listresult = fundingMainServiece.fun_option_reg(optionVo);

	         if(listresult > 0) {
	            pw.println("<script>alert('상품 등록 성공');location.href='" + request.getContextPath() + "'" + "</script>");
	         }
	         //pw.println("<script>alert('상품 등록 성공');location.href='" + request.getContextPath() + "'" + "</script>");
	      } else {
	         pw.println("<script>alert('상품 등록 실패');location.href='" + request.getContextPath() + "/mypage/mypage.do'" + "</script>");
	      }
	      pw.flush();
	      

	   }

	
	/* 펀딩 미리보기 페이지*/
	@RequestMapping(value = "/funding_view.do",method = RequestMethod.POST)
	public String funding_view( FundingMainVO vo, Model model, 
			MultipartFile funding_thumbnail_temp ,
			MultipartFile funding_Detail_temp,
			MultipartFile funding_Notice_temp,
			HttpServletRequest request
			,String funding_option_name, int []funding_option_price, String funding_option_detail, 
			int []funding_option_stock) throws ParseException, IllegalStateException, IOException {
		
		String path = request.getSession().getServletContext().getRealPath("/resources/upload/funding");
		File dir = new File(path);
		
		/*
		 * String extension = org_ThumName.substring(org_ThumName.lastIndexOf("."));
		 * //파일 확장자 String savedThumName = UUID.randomUUID() + extension; //저장될 파일 명
		 */		
		String org_ThumName = funding_thumbnail_temp.getOriginalFilename();
		String org_DetailName = funding_Detail_temp.getOriginalFilename();
		String org_NoticeName = funding_Notice_temp.getOriginalFilename();
		
		
		if (!dir.exists()) { // 해당 디렉토리가 존재하지 않는 경우
			dir.mkdirs(); // 경로의 폴더가 없는 경우 상위 폴더에서부터 전부 생성
		}

		if (!org_ThumName.isEmpty()) { // 업로드할 파일 명이 존재하는 경우
			funding_thumbnail_temp.transferTo(new File(path, org_ThumName));
		}
		
		if(!org_DetailName.isEmpty()) {
			funding_Detail_temp.transferTo(new File(path, org_DetailName));
		}
		
		if(!org_NoticeName.isEmpty()) {
			funding_Notice_temp.transferTo(new File(path, org_NoticeName));
		}
		
		 String from = vo.getFunding_end_date()+" 00:00:00";;
		 SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date to = fm.parse(from);
		 long d1 = to.getTime();
		
		  Calendar c1 = Calendar.getInstance(); 
		  long today = c1.getTimeInMillis();
		  
		  long diffSec = (d1 - today) / 1000; //초 차이 
		  long diffDays = diffSec /(24*60*60); //일자수 차이
		  
		  model.addAttribute("funding", vo); 
		  model.addAttribute("difftime", diffDays);
		  
		  model.addAttribute("org_ThumName",org_ThumName);
		  model.addAttribute("org_DetailName",org_DetailName);
		  model.addAttribute("org_NoticeName",org_NoticeName);
		  
		
		String []strName = funding_option_name.split(",");
		String []strDetail = funding_option_detail.split(",");
		
		List<Funding_optionVO> optionVo = new ArrayList<Funding_optionVO>();
		
		for(int i=0; i<funding_option_price.length; i++) {
			Funding_optionVO voo = new Funding_optionVO();
			voo.setFunding_option_name( strName[i]);
			voo.setFunding_option_price(funding_option_price[i]);
			voo.setFunding_option_detail(strDetail[i]);
			voo.setFunding_option_stock(funding_option_stock[i]);
			optionVo.add(voo);
			
		}
		
		/*옵션 리스트 모델에 담기*/
		model.addAttribute("optionList", optionVo);
		
		return "mypage/funding_view"; 
	}

	@RequestMapping(value = "/info_funding.do")
	public String info_funding(Model model, HttpServletRequest request) {
		// 세션에 있는 사용자의 정보를 가져옴
		HttpSession session = request.getSession();
		MemberVO login = (MemberVO) session.getAttribute("login");
		MemberVO member = mypageService.selectOne(login);
		model.addAttribute("member", member);
		
		//펀딩리스트
//		List<FundingMainVO> mfl = mypageService.myFundingList(login.getMember_idx());
//		model.addAttribute("myFundingList",mfl);
	
		//펀딩리스트
		List<FundingInfoDetailVO> mfl = mypageService.myFundingList2(login.getMember_idx());
		model.addAttribute("myFundingList",mfl);
		
		return "mypage/info_funding";
	}
	
	@RequestMapping(value = "/info_store.do")
	public String info_store(Model model, HttpServletRequest request) {
		// 세션에 있는 사용자의 정보를 가져옴
		HttpSession session = request.getSession();
		MemberVO login = (MemberVO) session.getAttribute("login");
		MemberVO member = mypageService.selectOne(login);
		model.addAttribute("member", member);
		
		//스토어리스트
		List<StoreInfoDetailVO> msl = mypageService.myStoreList(login.getMember_idx());
		model.addAttribute("myStoreList",msl);
		
		return "mypage/info_store";
	}

	@RequestMapping(value = "/info_funding_detail.do", method = RequestMethod.GET)
	public String info_funding_detail(Model model, FundingMainVO vo, HttpServletRequest request, @RequestParam("funding_order_idx") int funding_order_idx) {
		
		// 세션에 있는 사용자의 정보를 가져옴
		HttpSession session = request.getSession();
		MemberVO login = (MemberVO) session.getAttribute("login");
		MemberVO member = mypageService.selectOne(login);
		model.addAttribute("member", member);
		
		System.out.println("콘트롤러로 가져온 FUNDING_ORDER_IDX : "+funding_order_idx);
		
		//funding_idx와 member_idx를 통한 funding_MainVO와 funding_orderVO가져오기
		//위의 방식을 고쳐 funding_order_idx만으로 funding_orderVO를 가져올것이다
		model.addAttribute("detail", mypageService.fundingDetail(funding_order_idx));
			
		//funding_order_idx를 가지고 funding_order_payVO가지고 오기
		model.addAttribute("pay",mypageService.fundingPayDetail(funding_order_idx));
		
		//funding_order_idx를 가지고 funding_expressVO가지고 오기
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("member_idx",login.getMember_idx());
		param.put("funding_order_idx",funding_order_idx);
		model.addAttribute("express",mypageService.fundingExpressDetail(param));
		
		//funding_order_option은 따로 리스트형식으로 가져오기 - vo두개로 나눌것 첫번쨰껀 Funding_optionVO 두번째껀 Funding_order_optionVO
		model.addAttribute("option",mypageService.fundingOptionDetail(funding_order_idx));
		
		return "mypage/info_funding_detail";
	}

	@RequestMapping(value = "/info_store_detail.do", method = RequestMethod.GET)
	public String info_store_detail(Model model, FundingMainVO vo, HttpServletRequest request, @RequestParam("store_order_idx") int store_order_idx) {
		
		// 세션에 있는 사용자의 정보를 가져옴
		HttpSession session = request.getSession();
		MemberVO login = (MemberVO) session.getAttribute("login");
		MemberVO member = mypageService.selectOne(login);
		model.addAttribute("member", member);
		
		System.out.println("콘트롤러로 가져온 store_ORDER_IDX : "+store_order_idx);
		
		//store_order_idx로 store_orderVO를 가져오기
		model.addAttribute("detail", mypageService.storeDetail(store_order_idx));
			
		//store_order_idx를 가지고 store_order_payVO가지고 오기
		model.addAttribute("pay",mypageService.storePayDetail(store_order_idx));
		
		//store_order_idx를 가지고 store_expressVO가지고 오기
		model.addAttribute("express",mypageService.storeExpressDetail(store_order_idx));
		
		//store_order_option은 따로 리스트형식으로 가져오기 - vo두개로 나눌것 첫번쨰껀 store_optionVO 두번째껀 store_order_optionVO
		model.addAttribute("option",mypageService.storeOptionDetail(store_order_idx));
		
		return "mypage/info_store_detail";
	}

	@RequestMapping(value = "/info_zzim.do")
	public String info_zzim(Model model, FundingMainVO vo, HttpServletRequest request) throws Exception {
		
		// 세션에 있는 사용자의 정보를 가져옴
		HttpSession session = request.getSession();
		MemberVO login = (MemberVO) session.getAttribute("login");
		MemberVO member = mypageService.selectOne(login);
		model.addAttribute("member", member);
		// 찜리스트
		List<FundingMainVO> mzl = mypageService.myZzimList(login.getMember_idx());
		model.addAttribute("myZzimList",mzl);
		
		return "mypage/info_zzim";
	}

	@RequestMapping(value = "/my_info.do")
	public String my_info(Model model, HttpServletRequest request) {

		// 세션에 있는 사용자의 정보를 가져옴
		HttpSession session = request.getSession();
		MemberVO login = (MemberVO) session.getAttribute("login");
		MemberVO member = mypageService.selectOne(login);
		model.addAttribute("member", member);
		
		// 펀딩리스트4개씩
		List<FundingInfoDetailVO> s4f = mypageService.select4Funding(login.getMember_idx());
		model.addAttribute("select4Funding",s4f);
		
		return "mypage/my_info";
		
	}

	@RequestMapping(value = "/my_info_modify.do")
	public String my_info_modify(Model model, HttpServletRequest request) {

		// 세션에 있는 사용자의 정보를 가져옴
		HttpSession session = request.getSession();
		MemberVO login = (MemberVO) session.getAttribute("login");
		MemberVO member = mypageService.selectOne(login);
		model.addAttribute("member", member);

		return "mypage/my_info_modify";
	}

	@RequestMapping(value = "/upload.do")
	public String upload(HttpServletRequest request, @RequestParam("fff") MultipartFile file, FileUploadVO vo)
			throws IllegalStateException, IOException {

		HttpSession session = request.getSession();
		MemberVO login = (MemberVO) session.getAttribute("login");
		System.out.println("file idx : " + login.getMember_idx());
		System.out.println("file photo : " + login.getMember_photo());

		// 절대경로가 아닌
		// 톰캣이 카피를 떠서 사용하는 경로 -> 실시간 반영
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
		System.out.println("경로 : " + path);

		File dir = new File(path);
		if (!dir.exists()) { // 해당 디렉토리가 존재하지 않는 경우
			dir.mkdirs(); // 경로의 폴더가 없는 경우 상위 폴더에서부터 전부 생성
		}

		if (!file.getOriginalFilename().isEmpty()) { // 업로드할 파일 명이 존재하는 경우
			file.transferTo(new File(path, file.getOriginalFilename()));
		}

		//System.out.println("originalFilename : " + file.getOriginalFilename());
		vo.setPath(file.getOriginalFilename());

		// update_photo메소드로 originalFilename명을 넣어줌
		mypageService.update_photo(vo);

		return "redirect:my_info_modify.do";
	}

	@RequestMapping(value = "/profileUpdate.do")
	public String profileUpdate(MemberVO vo, HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		MemberVO login = (MemberVO) session.getAttribute("login");

		vo.setMember_idx(login.getMember_idx());

		// DB에 수정처리
		int result = mypageService.update_profile(vo);

		if (result > 0) {
			return "redirect:my_info.do";
		} else {
			System.out.println("수정 실패");
			return "redirect:/";
		}

	}
	
	//비밀번호 변경
	@ResponseBody
	@RequestMapping(value = "/changePw.do", method = RequestMethod.POST)
	public String changePw(@RequestParam Map<String, Object> param, HttpServletRequest request) {

		HttpSession session = request.getSession();
		MemberVO login = (MemberVO) session.getAttribute("login");

		System.out.println("member_idx : "+login.getMember_idx());
		System.out.println("member_password_old : "+param.get("member_password_old"));
		System.out.println("member_password_new : "+param.get("member_password_new"));
		
		Map<String, Object> changePwParam = new HashMap<String, Object>();
		changePwParam.put("member_password_old", param.get("member_password_old"));
		changePwParam.put("member_password_new", param.get("member_password_new"));
		changePwParam.put("member_idx",login.getMember_idx());
		
		// DB에 수정처리
		int result = mypageService.changePw(changePwParam);

		if (result > 0) {
			return "s";
		} else {
			System.out.println("수정 실패");
			return "f";
		}

	}
	
	
	//주소 변경
	@ResponseBody
	@RequestMapping(value = "/changeExpress.do", method = RequestMethod.POST)
	public String changeExpress(@RequestParam Map<String, Object> param) {

		Map<String, Object> changeExpressParam = new HashMap<String, Object>();
				changeExpressParam.put("funding_express_name", param.get("funding_express_name"));
				changeExpressParam.put("funding_express_phone", param.get("funding_express_phone"));
				changeExpressParam.put("funding_express_postnum", param.get("funding_express_postnum"));
				changeExpressParam.put("funding_express_addr1", param.get("funding_express_addr1"));
				changeExpressParam.put("funding_express_addr2", param.get("funding_express_addr2"));
				changeExpressParam.put("funding_order_idx", param.get("funding_order_idx"));
				
				// DB에 수정처리
				int result = mypageService.changeExpress(changeExpressParam);
		
				if (result > 0) {
					return "s";
				} else {
					System.out.println("수정 실패");
					return "f";
				}
		
	}
	
	//펀딩 취소
	@RequestMapping(value = "/fundingWithdraw.do", method = RequestMethod.POST)
	public String fundingWithdraw(@RequestParam("funding_order_idx") int funding_order_idx, HttpServletRequest request) {

		int result = mypageService.fundingWithdraw(funding_order_idx);
		if(result > 0 ) {
			System.out.println("펀딩 취소 성공");
			//이젠 페이지로 돌아간다
			String referer = request.getHeader("Referer");
		    return "redirect:"+ referer;
		    
		}else {
			System.out.println("펀딩 취소 실패");
			//이젠 페이지로 돌아간다
			String referer = request.getHeader("Referer");
		    return "redirect:"+ referer;
		}
	}
	//구매 취소
	@RequestMapping(value = "/storeWithdraw.do", method = RequestMethod.POST)
	public String storeWithdraw(Model model, @RequestParam("store_order_idx") int store_order_idx, HttpServletRequest request) {
		int result = mypageService.storeWithdraw(store_order_idx);
		if(result > 0 ) {
			System.out.println("구매 취소 성공");
			//이젠 페이지로 돌아간다
			model.addAttribute("msg", "구매가 취소되었습니다");
			String referer = request.getHeader("Referer");
		    return "redirect:"+ referer;
		}else {
			System.out.println("구매 취소 실패");
			//이젠 페이지로 돌아간다
			String referer = request.getHeader("Referer");
		    return "redirect:"+ referer;
		}
	}
	
		
	
	// 공지사항 이동
	@RequestMapping(value = "/notice_list.do")
	public String notice_list() {	
		return "notice/notice_list";
	}
	
	//펀딩 뷰
	@RequestMapping(value = "/view.do", method = RequestMethod.GET)
	public String read(@RequestParam Map<String, Object> paramMap, FundingCommunityVO fcvo, FundingMainVO vo, Model model, HttpSession session, HttpServletRequest request) throws Exception{
		
		//funding_idx에 따른 뷰페이지 정보 가져오기
		model.addAttribute("read", fms.read(vo.getFunding_idx()));		
		
		//세션사용자정보 가져옴
		session = request.getSession();
		MemberVO login = (MemberVO)session.getAttribute("login");
		MemberVO member = mypageService.selectOne(login);
		model.addAttribute("member",member);	
		//펀딩 커뮤니티 댓글 리스트
		List<FundingCommunityVO> fundingCommunityCommentList =fms.readFundingCommunityComent(fcvo);
		model.addAttribute("fundingCommunityCommentList", fundingCommunityCommentList);
	
		//펀딩 qna 댓글 리스트
		model.addAttribute("qnaList", fms.getQnaList(paramMap));
		
		return "funding/view";
	}
	
	// 찜 delete
	@RequestMapping(value ="/deleteZzim.do", method= RequestMethod.GET)
	public String deleteZzim(HttpServletRequest request, FundingMainVO vo, Model model){
		HttpSession session = request.getSession();
		MemberVO login = (MemberVO) session.getAttribute("login");
		
		Map<String, Integer> deleteZzim = new HashMap<String, Integer>();
		deleteZzim.put("member_idx", login.getMember_idx());
		deleteZzim.put("funding_idx", vo.getFunding_idx());
		
		int result = mypageService.deleteZzim(deleteZzim);
		if(result > 0) { //찜성공
			return "redirect:info_zzim.do";
		}else { //찜실패
			System.out.println("찜실패");
			return "redirect:info_zzim.do";
		}
	}
	
	/*펀딩 수정_수량 페이지*/
	@RequestMapping(value= "/funding_modify.do", method = RequestMethod.GET)
	public String funding_modify(int funding_idx, Model model) {
		
		// 펀딩 내용 불러오기
		FundingMainVO vo =  fms.select_fundingOne(funding_idx);
		model.addAttribute("funding", vo);
		
		//펀딩 옵션 불러오기
		List<Funding_optionVO> FundingOptionVo = fms.select_fundingOption(funding_idx);
		if(FundingOptionVo.size() >0) {
			
			model.addAttribute("optionList", FundingOptionVo);
		}
		
		return "mypage/funding_modify";
	}
	
	/*펀딩 수량 값 변경*/
	@RequestMapping(value= "/funding_modify.do", method = RequestMethod.POST)
	@ResponseBody
	public int funding_modify2(Model model , HttpServletRequest req ) {
		System.out.println("메소드 들어옴");
			
		String[] option_idx = req.getParameterValues("option_idx");
		String[] funding_option_plus = req.getParameterValues("funding_option_plus");
		Funding_optionVO vo = new Funding_optionVO();
		int result = 1;
		for(int i = 0; i<option_idx.length;i++) {
			if(!funding_option_plus[i].equals("0")) {
				vo.setFunding_option_idx(Integer.parseInt(option_idx[i]));
				System.out.println("idx : "+option_idx[i]);
				vo.setFunding_option_stock(Integer.parseInt(funding_option_plus[i]));
				System.out.println("수량"+funding_option_plus[i]);
				result = fms.addStock(vo);
				if(result == 0) {
					return 0;
				}else {
					result = 1;
				}
			}
		}
		return result;
	}
	
	/*펀딩 제품 설명 pdf 변경*/
	@RequestMapping(value="funding_modify_content.do", method=RequestMethod.GET)
	public String modify_content(int funding_idx, Model model,int check) {
		System.out.println("get메소드" +funding_idx);
		FundingMainVO vo =  fms.select_fundingOne(funding_idx);
		model.addAttribute("funding", vo);
		model.addAttribute("check",check);
		model.addAttribute("funding_idx", funding_idx);
		return "mypage/funding_modify_file";
	}
	
	/*펀딩 공지사항 변경*/
	@RequestMapping(value="funding_modify_notice.do", method=RequestMethod.GET)
	public String modify_notice(int funding_idx, Model model,int check) {
		System.out.println("get메소드" +funding_idx);
		FundingMainVO vo =  fms.select_fundingOne(funding_idx);
		model.addAttribute("funding", vo);
		model.addAttribute("check",check);
		model.addAttribute("funding_idx", funding_idx);
		return "mypage/funding_modify_file";
	}
	
	/*펀딩 컨텐츠 변경*/
	@RequestMapping(value = "funding_update_conetent.do",method=RequestMethod.POST)
	public String update_content(MultipartFile funding_Detail_temp , HttpServletRequest request , Model model, int funding_idx, int flag) throws IllegalStateException, IOException {
		
		
		String path = request.getSession().getServletContext().getRealPath("/resources/upload/funding");
		
		File dir = new File(path);
		
		String org_DetailName = funding_Detail_temp.getOriginalFilename();
		
		if (!dir.exists()) { // 해당 디렉토리가 존재하지 않는 경우
			dir.mkdirs(); // 경로의 폴더가 없는 경우 상위 폴더에서부터 전부 생성
		}
		
		if(!org_DetailName.isEmpty()) {
			funding_Detail_temp.transferTo(new File(path, org_DetailName));
		}
		FundingMainVO vo =  fms.select_fundingOne(funding_idx);
		model.addAttribute("funding", vo);
		model.addAttribute("file_name" ,org_DetailName);
		model.addAttribute("flag", flag);
		
	
			return "mypage/funding_view2";
	}
	
	/*펀딩 공지사항 변경*/
	@RequestMapping(value = "funding_update_notice.do",method=RequestMethod.POST)
	public String update_notice(MultipartFile funding_Notice_temp , HttpServletRequest request , Model model, int funding_idx, int flag) throws IllegalStateException, IOException {
		
		
		String path = request.getSession().getServletContext().getRealPath("/resources/upload/funding");
		
		File dir = new File(path);
		
		String org_DetailName = funding_Notice_temp.getOriginalFilename();
		
		if (!dir.exists()) { // 해당 디렉토리가 존재하지 않는 경우
			dir.mkdirs(); // 경로의 폴더가 없는 경우 상위 폴더에서부터 전부 생성
		}
		
		if(!org_DetailName.isEmpty()) {
			funding_Notice_temp.transferTo(new File(path, org_DetailName));
		}
		FundingMainVO vo =  fms.select_fundingOne(funding_idx);
		model.addAttribute("funding", vo);
		model.addAttribute("file_name" ,org_DetailName);
		model.addAttribute("flag", flag);
		
	
			return "mypage/funding_view2";
	}
	
	
	
	@RequestMapping(value="update_file.do",method = RequestMethod.POST)
	public void update_file(int funding_idx, int check, String funding_content, String funding_notice, 
			HttpServletResponse response, HttpServletRequest request) throws IOException {
		//프로젝트 pdf 업데이트
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		HashMap<String,Object> map2 = new HashMap<String,Object>();
		response.setContentType("text/html; charset=euc-kr;" );
		PrintWriter pw = response.getWriter();
		
		  String path = request.getSession().getServletContext().getRealPath("/resources/upload/funding");
		
		int result = 0;
		
		if(check == 0 ) {
			/*파일 난수이름으로 바꾸기*/
			   String content = funding_content;
			   File dir2 = new File(path+"/"+content);
			   String extension2 = content.substring(content.lastIndexOf("."));
			   String savedConName = UUID.randomUUID() + extension2; //저장될 파일 명
			   File newdir5 = new File(path+"/"+savedConName);
			   dir2.renameTo(newdir5);
			   funding_content = savedConName;
			
			System.out.println(funding_idx +" : " +check + " : " +funding_content +" : " +funding_notice );
			System.out.println("프로젝트 pdf 업데이트");
			map.put("funding_idx", funding_idx);
			map.put("checkValue", check);
			map.put("funding_content", funding_content);
			result = fms.update_content(map);
			if (result == 1) {
				pw.println("<script>alert('제품 설명 파일 수정완료');location.href='mypage2.do'</script>");
			}
			else {
				pw.println("<script>alert('제품 설명 파일 수정실패');window.history.back();</script>");
			}
		}else {
		     
	         String notice = funding_notice;
	         File dir3 = new File(path+"/"+notice);
	 		 String extension3 = notice.substring(notice.lastIndexOf("."));
	 		 String savedNotName = UUID.randomUUID() + extension3; //저장될 파일 명
	   
	         File newdir6 = new File(path+"/"+savedNotName);
		 	 dir3.renameTo(newdir6);
			
			funding_notice = savedNotName;
			
			System.out.println("공지사항 업데이트");
			map2.put("funding_idx", funding_idx);
			map2.put("checkValue", check);
			map2.put("funding_notice", funding_notice);
			result = fms.update_content(map2);
			
			if (result == 1) {
				pw.println("<script>alert('공지사항 파일 수정완료');location.href='mypage2.do'</script>");
			}
			else {
				pw.println("<script>alert('공지사항 파일 수정실패');window.history.back();</script>");
			}
			
		}
		pw.flush();
		
	}
		
	// 스토어 관리 페이지
	@RequestMapping(value= "/store_admin.do", method = RequestMethod.GET)
	public String store_admin(Model model, int store_idx) {
		
		// 스토어 관리 페이지
		List<StoreVO> sa = mypageService.store_admin(store_idx);
		model.addAttribute("admin", sa);
		
		return "mypage/store_admin";
	}
	
	// 배송 상태 변경하기
	@RequestMapping(value = "/update_Express.do")
	@ResponseBody
	public int updateFundingState(int store_order_idx) {
		
		// update 쿼리문
		int result = mypageService.update_Express(store_order_idx);
		
		return result;
	}
}
