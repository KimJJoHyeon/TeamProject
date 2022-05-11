package com.edu.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.edu.service.MypageService;
import com.edu.service.fundingMainService;
import com.edu.vo.FundingCommunityVO;
import com.edu.vo.FundingMainVO;
import com.edu.vo.FundingQnaVO;
import com.edu.vo.Funding_optionVO;
import com.edu.vo.Funding_orderVO;
import com.edu.vo.Funding_order_optionVO;
import com.edu.vo.MemberVO;
import com.edu.vo.PageMaker;
import com.edu.vo.Pagination;
import com.edu.vo.ZzimVO;


// Funding에 관련된 모든 동작 수행
@Controller
@RequestMapping(value = "/funding")
public class FundingController {
	
	@Autowired
	private fundingMainService fms;

	@Autowired
	private MypageService mypageService ;
	
	// 펀딩 메인페이지 카테고리
	@RequestMapping(value = "/main.do")
	public String main() {
		return "funding/main";
	}
	
	@RequestMapping(value = "/main_cat.do")
	public String main_cat() {
		return "funding/main_cat";
	}
	
	@RequestMapping(value = "/main_other.do")
	public String main_other() {
		return "funding/main_other";
	}
	
	
	// 펀딩 메인페이지에 리스트 출력	
	/*
	 * @RequestMapping(value="/main.do", method=RequestMethod.GET) public String
	 * listDog(Model model, Pagination page) throws Exception {
	 * 
	 * model.addAttribute("listDog",fms.listDog(page));
	 * 
	 * 
	 * PageMaker pageMaker = new PageMaker(); pageMaker.setPage(page);
	 * pageMaker.setTotalCount(fms.listDogCount());
	 * 
	 * model.addAttribute("pageMaker", pageMaker);
	 * 
	 * return "funding/main"; }
	 */

	/*
	 * @RequestMapping(value="/main_cat.do", method=RequestMethod.GET) public String
	 * listCat(Model model, Pagination page) throws Exception {
	 * 
	 * model.addAttribute("listCat",fms.listCat(page));
	 * 
	 * PageMaker pageMaker = new PageMaker(); pageMaker.setPage(page);
	 * pageMaker.setTotalCount(fms.listCatCount());
	 * 
	 * model.addAttribute("pageMaker", pageMaker);
	 * 
	 * return "funding/main_cat"; }
	 */
	/*
	 * @RequestMapping(value = "/main_infinite.do", method=RequestMethod.GET) public
	 * String main_infinite(HttpServletRequest request, HttpSession session) throws
	 * Exception { final int PAGE_ROW_COUNT = 9; //한 페이지에 몇개씩 표시할 것인지 int pageNum =
	 * 1; //보여줄 페이지의 번호를 일단 1이라고 초기값 지정 String strPageNum =
	 * request.getParameter("pageNum"); //페이지 번호가 파라미터로 전달되는지 읽어와 본다. if(strPageNum
	 * != null) { //만일 페이지 번호가 파라미터로 넘어 온다면 pageNum = Integer.parseInt(strPageNum);
	 * //숫자로 바꿔서 보여줄 페이지 번호로 지정한다. }
	 * 
	 * int startRowNum = 0 + (pageNum -1) * PAGE_ROW_COUNT; //보여줄 페이지의 시작 ROWNUM 0부터
	 * 시작 int endRowNum= pageNum * PAGE_ROW_COUNT; //보여줄 페이지의 끝 ROWNUM int rowCount
	 * = PAGE_ROW_COUNT;
	 * 
	 * String categorySelect = request.getParameter("categorySelect"); String
	 * condition= request.getParameter("condition");
	 * 
	 * if(categorySelect == null) { categorySelect = ""; }
	 * 
	 * if(condition == null) { condition = ""; } System.out.println(categorySelect);
	 * System.out.println(condition);
	 * 
	 * String encodedk = URLEncoder.encode(condition); //특수기호를 인코딩한 키워드를 준비한다.
	 * 
	 * FundingMainVO vo = new FundingMainVO(); //ROWNUM과 ROWCOUNT를 FundingMainVO 객체에
	 * 담는다. vo.setStartRowNum(startRowNum); vo.setEndRowNum(endRowNum);
	 * vo.setRowCount(rowCount);
	 * 
	 * if(condition.equals("sortNew")) { vo.setSortNew("a"); }else
	 * if(condition.equals("sortView")){ vo.setSortView("b"); }else
	 * if(condition.equals("sortPrice")) { vo.setSortPrice("c"); }
	 * if(categorySelect.equals("dog")) { vo.setDog("dog"); }else
	 * if(categorySelect.equals("cat")){ vo.setCat("cat"); }else
	 * if(categorySelect.equals("other")) { vo.setOther("other"); }
	 * 
	 * ArrayList<FundingMainVO> listMain = null; //ArrayList 객체의 참조값을 담을 지역변수를 미리
	 * 만든다. int totalRow = 0; //전체 row의 개수를담을 지역변수를 미리 만든다.
	 * 
	 * //글 목록 얻어오기 listMain = (ArrayList<FundingMainVO>) fms.listMain(vo);
	 * 
	 * // 글의 개수 totalRow = fms.listMainCount(vo);
	 * 
	 * // 전체 페이지의 갯수 int totalPageCount = (int)Math.ceil(totalRow /
	 * (double)PAGE_ROW_COUNT);
	 * 
	 * request.setAttribute("listMain", listMain);
	 * request.setAttribute("totalPageCount", totalPageCount);
	 * request.setAttribute("condition", condition);
	 * request.setAttribute("categorySelect", categorySelect);
	 * request.setAttribute("totalRow", totalRow); request.setAttribute("pageNum",
	 * pageNum);
	 * 
	 * System.out.println("startRowNum = " + startRowNum);
	 * System.out.println("endRowNum = " + endRowNum);
	 * System.out.println("rowCount = " + rowCount); System.out.println("totalRow= "
	 * + totalRow); System.out.println("totalPageCount=" + totalPageCount);
	 * System.out.println("=====================");
	 * 
	 * return "funding/main_infinite"; }
	 */
	/*
	 * @RequestMapping(value = "/main_cat.do", method=RequestMethod.GET) public
	 * String listCat(HttpServletRequest request, HttpSession session) throws
	 * Exception { final int PAGE_ROW_COUNT = 9; //한 페이지에 몇개씩 표시할 것인지 int pageNum =
	 * 1; //보여줄 페이지의 번호를 일단 1이라고 초기값 지정 String strPageNum =
	 * request.getParameter("pageNum"); //페이지 번호가 파라미터로 전달되는지 읽어와 본다. if(strPageNum
	 * != null) { //만일 페이지 번호가 파라미터로 넘어 온다면 pageNum = Integer.parseInt(strPageNum);
	 * //숫자로 바꿔서 보여줄 페이지 번호로 지정한다. }
	 * 
	 * int startRowNum = 0 + (pageNum -1) * PAGE_ROW_COUNT; //보여줄 페이지의 시작 ROWNUM 0부터
	 * 시작 int endRowNum= pageNum * PAGE_ROW_COUNT; //보여줄 페이지의 끝 ROWNUM int rowCount
	 * = PAGE_ROW_COUNT;
	 * 
	 * 
	 * String condition= request.getParameter("condition");
	 * 
	 * if(condition == null) { condition = ""; }
	 * 
	 * System.out.println(condition);
	 * 
	 * String encodedk = URLEncoder.encode(condition); //특수기호를 인코딩한 키워드를 준비한다.
	 * 
	 * FundingMainVO vo = new FundingMainVO(); //ROWNUM과 ROWCOUNT를 FundingMainVO 객체에
	 * 담는다. vo.setStartRowNum(startRowNum); vo.setEndRowNum(endRowNum);
	 * vo.setRowCount(rowCount);
	 * 
	 * if(condition.equals("sortNew")) { vo.setSortNew("a"); }else
	 * if(condition.equals("sortView")){ vo.setSortView("b"); }else
	 * if(condition.equals("sortPrice")) { vo.setSortPrice("c"); }
	 * ArrayList<FundingMainVO> listCat = null; //ArrayList 객체의 참조값을 담을 지역변수를 미리
	 * 만든다. int totalRow = 0; //전체 row의 개수를담을 지역변수를 미리 만든다.
	 * 
	 * //글 목록 얻어오기 listCat = (ArrayList<FundingMainVO>) fms.listCat(vo);
	 * 
	 * // 글의 개수 totalRow = fms.listCatCount();
	 * 
	 * // 전체 페이지의 갯수 int totalPageCount = (int)Math.ceil(totalRow /
	 * (double)PAGE_ROW_COUNT);
	 * 
	 * request.setAttribute("listCat", listCat);
	 * request.setAttribute("totalPageCount", totalPageCount);
	 * request.setAttribute("condition", condition);
	 * request.setAttribute("totalRow", totalRow); request.setAttribute("pageNum",
	 * pageNum);
	 * 
	 * return "funding/main_cat"; }
	 * 
	 * @RequestMapping(value="/main_other.do", method=RequestMethod.GET) public
	 * String listOther(Model model, Pagination page) throws Exception {
	 * 
	 * model.addAttribute("listOther",fms.listOther(page));
	 * 
	 * PageMaker pageMaker = new PageMaker(); pageMaker.setPage(page);
	 * pageMaker.setTotalCount(fms.listOtherCount());
	 * 
	 * model.addAttribute("pageMaker", pageMaker);
	 * 
	 * return "funding/main_other"; }
	 * 
	 * @RequestMapping(value = "/infinite_page.do", method=RequestMethod.GET) public
	 * String infinite_page(HttpServletRequest request, HttpSession session) throws
	 * Exception { final int PAGE_ROW_COUNT = 9; //한 페이지에 몇개씩 표시할 것인지 int pageNum =
	 * 1; //보여줄 페이지의 번호를 일단 1이라고 초기값 지정 String strPageNum =
	 * request.getParameter("pageNum"); //페이지 번호가 파라미터로 전달되는지 읽어와 본다. if(strPageNum
	 * != null) { //만일 페이지 번호가 파라미터로 넘어 온다면 pageNum = Integer.parseInt(strPageNum);
	 * //숫자로 바꿔서 보여줄 페이지 번호로 지정한다. }
	 * 
	 * int startRowNum = 0 + (pageNum -1) * PAGE_ROW_COUNT; //보여줄 페이지의 시작 ROWNUM 0부터
	 * 시작 int endRowNum= pageNum * PAGE_ROW_COUNT; //보여줄 페이지의 끝 ROWNUM int rowCount
	 * = PAGE_ROW_COUNT;
	 * 
	 * 
	 * String condition= request.getParameter("condition");
	 * 
	 * if(condition == null) { condition = ""; }
	 * 
	 * System.out.println(condition);
	 * 
	 * String encodedk = URLEncoder.encode(condition); //특수기호를 인코딩한 키워드를 준비한다.
	 * 
	 * FundingMainVO vo = new FundingMainVO(); //ROWNUM과 ROWCOUNT를 FundingMainVO 객체에
	 * 담는다. vo.setStartRowNum(startRowNum); vo.setEndRowNum(endRowNum);
	 * vo.setRowCount(rowCount);
	 * 
	 * if(condition.equals("sortNew")) { vo.setSortNew("a"); }else
	 * if(condition.equals("sortView")){ vo.setSortView("b"); }else
	 * if(condition.equals("sortPrice")) { vo.setSortPrice("c"); }
	 * ArrayList<FundingMainVO> listCat = null; //ArrayList 객체의 참조값을 담을 지역변수를 미리
	 * 만든다. int totalRow = 0; //전체 row의 개수를담을 지역변수를 미리 만든다.
	 * 
	 * //글 목록 얻어오기 listCat = (ArrayList<FundingMainVO>) fms.listCat(vo);
	 * 
	 * // 글의 개수 totalRow = fms.listCatCount();
	 * 
	 * // 전체 페이지의 갯수 int totalPageCount = (int)Math.ceil(totalRow /
	 * (double)PAGE_ROW_COUNT);
	 * 
	 * request.setAttribute("listCat", listCat);
	 * request.setAttribute("totalPageCount", totalPageCount);
	 * request.setAttribute("condition", condition);
	 * request.setAttribute("totalRow", totalRow); request.setAttribute("pageNum",
	 * pageNum);
	 * 
	 * return "funding/infinite_page"; }
	 */
	@RequestMapping(value = "/main.do", method=RequestMethod.GET)
	public String listMain(HttpServletRequest request, HttpSession session) throws Exception {
		final int PAGE_ROW_COUNT = 9; 							//한 페이지에 몇개씩 표시할 것인지
		final int displayPageNum = 3;							//페이징 번호 몇 개
		
		int pageNum = 1; 										//보여줄 페이지의 번호를 일단 1이라고 초기값 지정
		String strPageNum = request.getParameter("pageNum"); 	//페이지 번호가 파라미터로 전달되는지 읽어와 본다.
		if(strPageNum != null) {								//만일 페이지 번호가 파라미터로 넘어 온다면
			pageNum = Integer.parseInt(strPageNum);				//숫자로 바꿔서 보여줄 페이지 번호로 지정한다.
		}															
		
		int startRowNum = (pageNum -1) * PAGE_ROW_COUNT;	//보여줄 페이지의 시작 ROWNUM  0부터 시작
		int endRowNum= startRowNum * PAGE_ROW_COUNT - 1;				//보여줄 페이지의 끝 ROWNUM
		int rowCount = PAGE_ROW_COUNT;
		
		String categorySelect = request.getParameter("categorySelect");
		String condition= request.getParameter("condition");
		
		if(categorySelect == null) {
			categorySelect = "";
		}
		
		if(condition == null) {
			condition = "";
		}
		System.out.println(categorySelect);
		System.out.println(condition);
		
		String encodedk = URLEncoder.encode(condition);			//특수기호를 인코딩한 키워드를 준비한다.
		
		FundingMainVO vo = new FundingMainVO();							//ROWNUM과 ROWCOUNT를 FundingMainVO 객체에 담는다.
		vo.setStartRowNum(startRowNum);
		vo.setEndRowNum(endRowNum);
		vo.setRowCount(rowCount);
		
		
		

		if(condition.equals("sortNew")) {
			vo.setSortNew("a");
		}else if(condition.equals("sortView")){
			vo.setSortView("b");
		}else if(condition.equals("sortPrice")) {
			vo.setSortPrice("c");
		}
		if(categorySelect.equals("dog")) {
			vo.setDog("dog");
		}else if(categorySelect.equals("cat")){
			vo.setCat("cat");
		}else if(categorySelect.equals("other")) {
			vo.setOther("other");
		}
		
		ArrayList<FundingMainVO> listMain = null;					//ArrayList 객체의 참조값을 담을 지역변수를 미리 만든다.
		int totalRow = 0;										//전체 row의 개수를담을 지역변수를 미리 만든다.
		
		//글 목록 얻어오기
		listMain = (ArrayList<FundingMainVO>) fms.listMain(vo);
		
		// 글의 개수
		totalRow = fms.listMainCount(vo);
		
		// 전체 페이지의 갯수
		int totalPageCount = (int)Math.ceil(totalRow / (double)PAGE_ROW_COUNT);
		
		int endPage = (int) (Math.ceil(pageNum / (double)displayPageNum) * displayPageNum);
		int startPage = (endPage - displayPageNum) + 1;
		
		int tempEndPage = (int) (Math.ceil(totalRow / (double)PAGE_ROW_COUNT));
		if (endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		boolean prev = startPage == 1 ? false : true;
		boolean next = endPage * PAGE_ROW_COUNT >= totalRow ? false : true;
		
		
		
		request.setAttribute("listMain", listMain);
		request.setAttribute("totalPageCount", totalPageCount);
		request.setAttribute("condition", condition);
		request.setAttribute("categorySelect", categorySelect);
		request.setAttribute("totalRow", totalRow);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startRowNum", startRowNum);
		request.setAttribute("endRowNum", endRowNum);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);
		request.setAttribute("endPage", endPage);
		request.setAttribute("startPage", startPage);
	
		return "funding/main";
	}

	
	
	//펀딩 뷰
	@RequestMapping(value = "/view.do", method = RequestMethod.GET)
	public String read(@RequestParam Map<String, Object> paramMap, FundingCommunityVO fcvo, FundingMainVO vo, Model model, HttpSession session, HttpServletRequest request, Pagination page) throws Exception{
		
		//funding_idx에 따른 뷰페이지 정보 가져오기
		model.addAttribute("read", fms.read(vo.getFunding_idx()));		
		
		//세션사용자정보 가져옴
		session = request.getSession();
		MemberVO login = (MemberVO)session.getAttribute("login");
		MemberVO member = mypageService.selectOne(login);
		model.addAttribute("member",member);	
		//펀딩 커뮤니티 댓글 리스트
		List<FundingCommunityVO> fundingCommunityCommentList =fms.readFundingCommunityComent(vo.getFunding_idx());
		model.addAttribute("fundingCommunityCommentList", fundingCommunityCommentList);
		
		PageMaker pageMaker = new PageMaker(); pageMaker.setPage(page);
		pageMaker.setTotalCount(fms.countFundingCommunityComment(fcvo));		 
		model.addAttribute("pageMaker", pageMaker);
	
		//펀딩 qna 댓글 리스트
		model.addAttribute("qnaList", fms.getQnaList(paramMap));
		
		return "funding/view";
	}
	//오더 카운트
	@RequestMapping(value ="/read_funding_form", method= RequestMethod.POST)
	@ResponseBody
	public int orderCount(Funding_orderVO vo) throws Exception {
		return fms.orderCount(vo);
	}
	
	//펀딩 커뮤니티 댓글 작성 ajax로 불러옴
	@RequestMapping(value ="/serialize", method= RequestMethod.POST)
	@ResponseBody
	public int serialize(FundingCommunityVO vo) throws Exception {
		//return vo.getFunding_detail_community_content() + vo.getFunding_idx() + vo.getMember_idx() + vo.getFunding_detail_community_category();
		return fms.writeFundingCommunityComment(vo);
	}
	
	//펀딩 커뮤니티 댓글 수정 ajax로 불러옴
	@RequestMapping(value ="/commuModify", method= RequestMethod.POST)
	@ResponseBody
	public void commuModify(FundingCommunityVO vo) throws Exception {
		//return vo.getFunding_detail_community_content() + vo.getFunding_idx() + vo.getMember_idx() + vo.getFunding_detail_community_category();
		fms.modifyFundingCommunityComment(vo);
	}
	//펀딩 커뮤니티 댓글 삭제ajax로 불러옴
	@RequestMapping(value ="/commuDelete", method= RequestMethod.POST)
	@ResponseBody
	public void commuDelete(FundingCommunityVO vo) throws Exception {
		//return vo.getFunding_detail_community_content() + vo.getFunding_idx() + vo.getMember_idx() + vo.getFunding_detail_community_category();
		fms.deleteFundingCommunityComment(vo);
	}
	
	//펀딩 qna 작성 ajax로 불러옴
	@RequestMapping(value ="/qnaInsert", method= RequestMethod.POST)
	@ResponseBody
	public Object qnaInsert(@RequestParam Map<String, Object> paramMap){
		
		//리턴값
		Map<String, Object> retVal = new HashMap<String, Object>();
		int result = fms.qnaInsert(paramMap);
		if(result>0) {
			retVal.put("code", "OK");
			retVal.put("funding_idx", paramMap.get("funding_idx"));
			retVal.put("member_idx", paramMap.get("member_idx"));
			retVal.put("funding_qna_idx", paramMap.get("funding_qna_idx"));
			retVal.put("parent_id", paramMap.get("parent_id"));
			retVal.put("funding_qna_secret", paramMap.get("funding_qna_secret"));
			retVal.put("message", "등록 성공!");
		}else {
			retVal.put("code", "FAIL");
			retVal.put("message", "등록 실패!");
		}
		return retVal;
	}
	
	//펀딩 qna 상태 업데이트
	@RequestMapping(value ="/qnaAnswerDone", method= RequestMethod.POST)
	@ResponseBody
	public int qnaAnswerDone(FundingQnaVO vo) throws Exception{
		return fms.qnaAnswerDone(vo);
	}
	// 펀딩 qna 삭제
	@RequestMapping(value ="/qnaDelete", method= RequestMethod.POST)
	@ResponseBody
	public void deleteFundingQna(FundingQnaVO vo) throws Exception {
		fms.deleteFundingQna(vo);
	}
	// 펀딩 qna 수정
	@RequestMapping(value ="/qnaModify", method= RequestMethod.POST)
	@ResponseBody
	public void modifyFundingQna(FundingQnaVO vo) throws Exception {
		fms.modifyFundingQna(vo);
	}
	
	// 찜 insert
	@RequestMapping(value ="/insertZzim", method= RequestMethod.POST)
	@ResponseBody
	public Object insertZzim(@RequestParam Map<String, Object> paramMap){
		
		Map<String, Object> retVal = new HashMap<String, Object>();
		int result = fms.insertZzim(paramMap);
		if(result>0) {
			retVal.put("code", "OK");
			retVal.put("message", "찜 성공!");
		}else {
			retVal.put("code", "FAIL");
			retVal.put("message", "찜 실패!");
		}
		return retVal;
	}
	
	// 찜 select
	@RequestMapping(value ="/selectZzim", method= RequestMethod.POST)
	@ResponseBody
	public Object selectZzim(@RequestParam Map<String, Object> paramMap){
		Map<String, Object> selectZzim = new HashMap<String, Object>();
		List<ZzimVO> result = fms.selectZzim(paramMap);		
		return result;		
	}
	
	// 찜 delete
	@RequestMapping(value ="/deleteZzim", method= RequestMethod.POST)
	@ResponseBody
	public Object deleteZzim(@RequestParam Map<String, Object> paramMap){
		Map<String, Object> deleteZzim = new HashMap<String, Object>();
		int result = fms.deleteZzim(paramMap);
		return result;
	}

	
	
	
	
	
	// 이동
	@RequestMapping(value = "/view.do")
	public String view() {
		return "funding/view";
	}
	
	// 옵션 페이지
	@RequestMapping(value = "/option.do", method = RequestMethod.GET)
	public String option(Model model, FundingMainVO mainvo, Funding_optionVO optionvo) throws Exception {
		
		model.addAttribute("read", fms.read(mainvo.getFunding_idx()));
		
		System.out.println();
		// 옵션 리스트 출력
		List<Funding_optionVO> optionlist = fms.list(optionvo);
		model.addAttribute("optionlist", optionlist);
		
		return "funding/option";
	}
	
	@RequestMapping(value = "/option.do", method = RequestMethod.POST)
	public String option(Model model, Funding_optionVO optionvo, HttpServletRequest request) throws Exception {
		
		// 옵션 리스트 출력
		List<Funding_optionVO> optionlist = fms.list(optionvo);
		model.addAttribute("optionlist", optionlist);
		
		//세션에 있는 사용자의 정보 가져옴
		HttpSession session = request.getSession();
		MemberVO login = (MemberVO)session.getAttribute("login");
		MemberVO member = fms.selectOne(login);
		model.addAttribute("member", member);
		
		return "funding/reserve";
	}
	
	// 결제 예약 페이지
	@RequestMapping(value = "/reserve.do", method = RequestMethod.POST)
	public void orderForm(Model model, Funding_orderVO ordervo, Funding_order_optionVO orderOptionvo, HttpServletResponse response) throws IOException {
		// 펀딩 주문 번호
		int result = fms.insertOrder(ordervo);
		
		// 펀딩 주문 옵션 저장
		int result2 = fms.insertOption(orderOptionvo);
		response.setContentType("text/html; charset=euc-kr");
		PrintWriter pw = response.getWriter();
		if(result2>0) {
			// 저장 완료
			pw.println("<script>alert('결제 예약이 완료되었습니다.');location.href='reserve_complete.do';</script>");
		}else {
			// 저장 안됭
			pw.println("<script>alert('결제 예약이 실패하었습니다.');location.href='reserve.do';</script>");
		}
		pw.flush();
		
	}
	
	@RequestMapping(value = "/reserve_complete.do")
	public String reserveComplete() {
		return "funding/reserve_complete";
	}
	
	
}
