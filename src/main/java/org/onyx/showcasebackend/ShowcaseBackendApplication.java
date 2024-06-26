package org.onyx.showcasebackend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.onyx.showcasebackend.Web.services.AdminService;
import org.onyx.showcasebackend.Web.services.CartService;
import org.onyx.showcasebackend.Web.services.ClientService;

import org.onyx.showcasebackend.dao.*;
import org.onyx.showcasebackend.entities.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class ShowcaseBackendApplication  {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AdminService adminService;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private FashionCollectionRepository fashionCollectionRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;



	@Autowired
	private CartService cartService;

	@Autowired
	private ClientService clientService;




	private static final Logger LOG = LoggerFactory.getLogger(ShowcaseBackendApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(ShowcaseBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			// read json and write to db
			ObjectMapper mapper = new ObjectMapper();

			// upload Client fixture
			TypeReference<List<Client>> clientTypeReference = new TypeReference<>(){};
			InputStream clientStream = TypeReference.class.getResourceAsStream("/fixtures/users.json");

			// upload Item fixture
			TypeReference<List<Item>> itemTypeReference = new TypeReference<>(){};
			InputStream itemStream = TypeReference.class.getResourceAsStream("/fixtures/items.json");


			// upload FashionCollection fixture
			TypeReference<List<FashionCollection>> collectionTypeReference = new TypeReference<>(){};
			InputStream collectionStream = TypeReference.class.getResourceAsStream("/fixtures/fashion-collection.json");


			// upload Admin fixture
			TypeReference<List<Admin>> adminTypeReference = new TypeReference<>(){};
			InputStream adminStream = TypeReference.class.getResourceAsStream("/fixtures/admins.json");

			try {
				List<Role> roles;
				roles = roleRepository.findAll();
				if(roles.isEmpty()) {
					roles = createRole();
					createPrivilege();
				}

				//Admins

				Role roleAdmin = roles.stream().filter(role->(role.getName().equals("ROLE_ADMIN"))).findFirst().get();

				List<Admin> admins = mapper.readValue(adminStream,adminTypeReference);

				admins.forEach((admin) -> {
					if(userRepository.findByEmail(admin.getEmail()) == null) {
						String password = passwordEncoder.encode(admin.getPassword());
						admin.setPassword(password);
						admin.setRole(roleAdmin);
						createAdmin(admin);
					}
				});
				LOG.info("Admin Saved !");


				// Clients
				Role roleUser = roles.stream().filter(role->(role.getName().equals("ROLE_USER"))).findFirst().get();

				List<Client> clients = mapper.readValue(clientStream,clientTypeReference);
				clients.forEach((client) -> {
					if(userRepository.findByEmail(client.getEmail()) == null) {
						client.setRole(roleUser);
						createClient(client);
					}
				});
				LOG.info("Client Saved !");


				// FashionCollections
				List<FashionCollection> fashionCollections = mapper.readValue(collectionStream, collectionTypeReference);
				fashionCollections.forEach((fashionCollection)->{
					System.out.println(fashionCollectionRepository.findFashionCollectionByName(fashionCollection.getName()));
					if(fashionCollectionRepository.findFashionCollectionByName(fashionCollection.getName()).size()==0){

						fashionCollectionRepository.save(fashionCollection);
					}
				});
				LOG.info("FashionCollection Saved !");


				// Items
				Random rand = new Random();
				List<Item> items = mapper.readValue(itemStream, itemTypeReference);
				items.forEach((item)->{
					if(itemRepository.findItemByName(item.getName())==null){
						int randomIndex = rand.nextInt(fashionCollections.size());
						FashionCollection randomCollection = fashionCollections.get(randomIndex);
						item.setFashionCollection(randomCollection);
						itemRepository.save(item);
					}
				});
				LOG.info("Items Saved !");


			} catch (IOException e){
				LOG.error(e.getMessage());
			}
		};
	}

	private List<Role> createRole(){

		List<Role> roles = new ArrayList<>();
		Stream.of("ROLE_USER","ROLE_ADMIN","ROLE_SUPER_ADMIN").forEach((role_name)-> {
			Role role = new Role();
			role.setName(role_name);
			roleRepository.save(role);
			roles.add(role);
		});
		return roles;
	}


	private void createAdmin(Admin admin){
		adminService.saveAdmin(admin);
	}

	private void createPrivilege(){
		Role roleUser = roleRepository.findByName("ROLE_USER");
		Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
		Stream.of(
				// User entity
				new Privilege("update","User", false, roleAdmin),
				new Privilege("update", "User", true,roleUser),
				new Privilege("list", "User", false,roleAdmin),
				new Privilege("list", "User",true,roleUser),

				// Cart entity
				new Privilege("update","Cart", false, roleAdmin),
				new Privilege("update", "Cart", true,roleUser),
				new Privilege("list", "Cart", false,roleAdmin),
				new Privilege("list", "Cart",true,roleUser)



		).forEach(privilege -> privilegeRepository.save(privilege) );
	}

	private void createClient(Client client){
		List<Item> items = new ArrayList<>();
		String password = passwordEncoder.encode(client.getPassword());
		client.setPassword(password);
		Cart cart = new Cart();
		cart.setItems(items);
		client.setCart(cart);
		Role role = roleRepository.findByName("ROLE_USER");
		client.setRole(role);
		cartService.saveCart(cart);
		clientService.saveClient(client);
	}

}
