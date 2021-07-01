package jp.ac.hcs.s3a126.task;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TaskController {
	@Autowired
	private TaskService taskService;
	
	/**
	 * タスク情報の表示、追加、削除を行う
	 */
	@PostMapping("/task")
	public String getTask(Principal principal, Model model) {
		
		
		TaskEntity taskEntity = taskService.selectAll(principal.getName());
		model.addAttribute("taskEntity", taskEntity);
		
		log.info("[" + principal.getName() + "]タスク:" + taskEntity);
		
		return "task/task";
		
	}
}