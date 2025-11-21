package com.webflux.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webflux.model.Tutorial;
import com.webflux.service.TutorialService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {
  @Autowired
  TutorialService tutorialService;
  
  @GetMapping("/tutorials")
  @ResponseStatus(HttpStatus.OK)
  public Flux<Tutorial> getAllTutorials(@RequestParam(required = false) String title) {
    if (title == null)
      return tutorialService.findAll();
    else
      return tutorialService.findByTitleContaining(title);
  }

  @GetMapping("/tutorials/{id}")
  public Mono<ResponseEntity<Tutorial>> getTutorialById(@PathVariable("id") int id) {
    return tutorialService.findById(id)
    		.map(tutorial->ResponseEntity.ok(tutorial))
    		.defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping("/tutorials")
  public Mono<ResponseEntity<Map<String,Object>>> createTutorial(@RequestBody Tutorial tutorial) {
	  
	  Tutorial toSave=new Tutorial(tutorial.getTitle(),tutorial.getDescription(),false);
	  
	  return tutorialService.save(toSave)
			  .map(saved->{
				  Map<String,Object> body=Collections.singletonMap("id",saved.getId( ));
				  return ResponseEntity.status(HttpStatus.CREATED).body(body);   // 201
			  })
			  .onErrorResume(ex ->
              Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build())
      );
  }

  @PutMapping("/tutorials/{id}")
  public Mono<ResponseEntity<Map<String, Object>>> updateTutorial(
          @PathVariable("id") int id,
          @RequestBody Tutorial tutorial) {

      return tutorialService.update(id, tutorial)
              .map(updated -> {
                  Map<String, Object> body = Collections.singletonMap("id", updated.getId());
                  return ResponseEntity.ok(body); // 200 with only ID
              })
              .defaultIfEmpty(ResponseEntity.notFound().build()); // 404 if no record
  }
  
  @DeleteMapping("/tutorials/{id}")
  public Mono<ResponseEntity<Void>> deleteTutorial(@PathVariable("id") int id) {
      return tutorialService.deleteById(id)
              .then(Mono.just(ResponseEntity.noContent().<Void>build())) 
              .onErrorResume(ex ->
                      Mono.just(ResponseEntity.notFound().build())       // e.g. id not found
              );
  }
  

  @DeleteMapping("/tutorials")
  public Mono<ResponseEntity<Void>> deleteAllTutorials() {
      return tutorialService.deleteAll()
              .then(Mono.just(ResponseEntity.noContent().<Void>build()));
  }

  @GetMapping("/tutorials/published")
  public Flux<Tutorial> findByPublished() {
    return tutorialService.findByPublished(true);
  }
}
