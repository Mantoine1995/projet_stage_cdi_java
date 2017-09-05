package com.umanteam.dadakar.admin.front.ctrl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.umanteam.dadakar.admin.front.dto.Account;
import com.umanteam.dadakar.admin.front.dto.Bannished;
import com.umanteam.dadakar.admin.front.dto.Message;
import com.umanteam.dadakar.admin.front.dto.User;
import com.umanteam.dadakar.admin.front.service.interfaces.IAccountService;
import com.umanteam.dadakar.admin.front.service.interfaces.IMessageService;
import com.umanteam.dadakar.admin.front.service.interfaces.IUserService;

@Controller
@RequestMapping(value="/user")
public class UserCtrl {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private IMessageService messageService;
	
	@Value("${img.path}")
	private String imgPath;
	
	@RequestMapping(value={"/", "index"})
	public ModelAndView index(Model model) {
		model.addAttribute("users", userService.findAll());
		model.addAttribute("imgPath", imgPath);
		return new ModelAndView("user/index", "bannished", new Bannished());
	}
	
	@RequestMapping(value="/message/{sid}:{rid}")
	public String userMessage(@PathVariable("sid") String sid, @PathVariable("rid") String rid, Model model) {
		LocalDateTime now = LocalDateTime.now();
		String month = now.getMonthValue() < 10 ? "0" + now.getMonthValue() : "" + now.getMonthValue() ;
		String day = now.getDayOfMonth() < 10 ? "0" + now.getDayOfMonth() : "" + now.getDayOfMonth();
		String hours = now.getHour() < 10 ? "0" + now.getHour() : "" + now.getHour();
		String minutes = now.getMinute() < 10 ? "0" + now.getMinute() : "" + now.getMinute();
		String date = now.getYear() + "-" + month + "-" + day + " " + hours + ":" + minutes;
		Message message = new Message(sid, rid, date, "Problème de profil", "");
		model.addAttribute("message", message);
		return "user/message";
	}
	
	@RequestMapping(value="/send", method= RequestMethod.POST)
	public String send(@ModelAttribute("message") Message message, BindingResult result, final RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			return "user/message";
		}
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "le message à bien été envoyé");	
		messageService.add(message);
		
		return "redirect:/user/";
	}
	
	@RequestMapping(value="/bannish/{id}")
	public String bannish(@PathVariable("id") String id) {
		User user = userService.findById(id);
		Account account = accountService.findById(user.getAccount().getAccountId());
		account.setBanned(!account.isBanned());
		account = accountService.update(account);
		user.setAccount(account);
		userService.update(user);
		return "redirect:/user/";
	}
	
	@RequestMapping(value="/bannishall", method=RequestMethod.POST)
	public ModelAndView bannishAll(@ModelAttribute Bannished bannished) {
		ModelAndView view = new ModelAndView("redirect:/user/");
		view.addObject("bannished", bannished);
		for(String id: bannished.getBanned()) {
			Account account = accountService.findById(id);
			System.out.println(account);
			User user = userService.findByAccountUsername(account.getUsername());
			System.out.println(user);
			account.setBanned(!account.isBanned());
			account = accountService.update(account);
			System.out.println(account);
			user.setAccount(account);
			userService.update(user);
		}
		return view;
	}
	
}
