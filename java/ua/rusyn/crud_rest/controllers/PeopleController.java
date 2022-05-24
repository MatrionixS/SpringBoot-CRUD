package ua.rusyn.crud_rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.rusyn.crud_rest.dao.PersonDAO;
import ua.rusyn.crud_rest.models.Person;
import ua.rusyn.crud_rest.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonValidator personValidator;

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonValidator personValidator, PersonDAO personDAO) {
        this.personValidator = personValidator;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("people",personDAO.index());
        return "views/people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,Model model){
        model.addAttribute("person" , personDAO.show(id));
        return "views/people/show";
    }
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "views/people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()){
            return "views/people/new";
        }
        personDAO.save(person);
     return  "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model , @PathVariable("id") int  id){
        model.addAttribute("person", personDAO.show(id));
        return "views/people/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,BindingResult bindingResult,@PathVariable("id")int id){
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()) {
            return "views/people/edit";
        }
        personDAO.update(id,person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}
