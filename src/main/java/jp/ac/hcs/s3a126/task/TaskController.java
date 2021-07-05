package jp.ac.hcs.s3a126.task;

import java.security.Principal;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TaskController {
	@Autowired
	private TaskService taskService;
	
	/**
	 * タスク情報の表示を行う
	 */
	@PostMapping("/task")
	public String getTask(Principal principal, Model model) {
		
		if(principal.getName().isEmpty()) {
			return "index";
		}
		TaskEntity taskEntity = taskService.selectAll(principal.getName());
		model.addAttribute("taskEntity", taskEntity);
		
		log.info("[" + principal.getName() + "]タスク一覧");
		
		return "task/task";
		
	}
	
	/**
	 * タスク情報の追加を行う
	 * @throws ParseException 
	 */
	@PostMapping("/task/insert")
	public String insertTask(@RequestParam(name = "comment", required = false) String comment,
							@RequestParam(name = "limitday", required =  false) String limitday, 
							Principal principal, Model model) throws ParseException {
		
		if((comment == "") || (comment.length() > 50) || (limitday == "")) {
			TaskEntity taskEntity = taskService.selectAll(principal.getName());
			model.addAttribute("taskEntity", taskEntity);
			
			log.info("[" + principal.getName() + "]タスク入力エラー");
			
			return "task/task";
		}
		
		taskService.insertOne(principal.getName(), comment, limitday);
		
		log.info("[" + principal.getName() + "]タスク追加:" + comment + " + " + limitday);
		
		
		TaskEntity taskEntity = taskService.selectAll(principal.getName());
		model.addAttribute("taskEntity", taskEntity);
		
		return "task/task";
		
	}
};