package by.filippov.spring.servises;

import by.filippov.spring.repository.AnimalRepo;
import by.filippov.spring.domain.Animal;
import by.filippov.spring.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {

  public final AnimalRepo animalRepo;

  public void addAnimal(Animal animal) {
    animalRepo.save(animal);
  }

  public List<Animal> findAllAnimal() {
    Iterable<Animal> animals = animalRepo.findAll();
    List<Animal> animalList = new ArrayList<>();
    animals.forEach(animalList::add);
    return animalList;
  }


  public void removeAnimal(Animal animal) {

    if(animal != null) {
      animal.setDeleted(true);
      animalRepo.save(animal);
    }
  }

  public void repairAnimal(Animal animal) {


    if(animal != null) {
      animal.setDeleted(false);
      animalRepo.save(animal);
    }
  }

  public Page<Animal> findAllAnimals(Pageable pageable) {
    return animalRepo.findAll(pageable);
  }

  public Page<Animal> findAllAnimals(String nameFilter, String speciesFilter, Pageable pageable) {

    if (StringUtils.isEmpty(nameFilter) && StringUtils.isEmpty(speciesFilter)) {
      return animalRepo.findAll(pageable);
    } else {
      return animalRepo.findByNameStartingWithIgnoreCaseAndSpeciesStartsWithIgnoreCase(nameFilter, speciesFilter, pageable);
    }
  }

  public List<Animal> findAllAnimalsByOwner(User user) {
    return animalRepo.findByAnimalOwner(user);
  }

}
