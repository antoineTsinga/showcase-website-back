
package org.onyx.showcasebackend.Web.services;



import org.onyx.showcasebackend.dao.PrivilegeRepository;
import org.onyx.showcasebackend.dao.UserRepository;

import org.onyx.showcasebackend.entities.Privilege;


import org.onyx.showcasebackend.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


/**
 * Service de vérification des privileges utilisateurs.
 */

@Service
public class AuthorizationSE {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PrivilegeRepository privilegeRepo;



/**
     * Vérifie l'autorisation pour les privileges qui ne sont pas sensé contenir de contraintes sur l'objet visé
     * @param action le type d'action demandée
     * @param entity l'objet visé
     * @return
     */

    public boolean can(String action, String entity) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        MyUserDetails currentUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //recuperation du privilege par action et par entité : logiquement il n'en existe qu'un par role avec cette action et cet objet
        Privilege privilege = privilegeRepo.findByActionAndEntityAndRole(action, entity, currentUser.getRole());
        //si privileges existe et qu'il n'attend pas de vérification de contrainte
        return (null != privilege && !privilege.isConstrained());


    }


    /**
     * Vérifie l'autorisations pour les privileges qui comportent des contraintes sur l'objet visé
     * @param action le type d'action demandée
     * @param entity l'objet visé
     * @param entityId l'id de l'objet visé
     * @return Vrai ou Faux
     */


    public boolean can(String action, String entity, Long entityId) {

        MyUserDetails currentUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean authorized = false;
        //recuperation du privilege par action et par entité : Il ne peut en exister qu'un par role
        Privilege privilege = privilegeRepo.findByActionAndEntityAndRole(action, entity, currentUser.getRole());

        if (null == privilege) {
            return false;
        }

        //implémentation des logiques métier de vérification des contraintes
        switch (entity){
            //Vérification si l'utilisateur est affecté au contrat via la table d'affectation UserContract

/*case "Cart":
                Optional<User> user = userRepo.findById(currentUser.getId());
                //Récuperation des contrats sur lesquel est affecté l'utilisateur
                List<Cart> userCart= user.get().getUserContracts();
                for (UserContract userContract : userContracts) {
                    if (userContract.getContract().getId().equals(entityId)) {
                        authorized = true;
                        break;
                    }
                }
                break;
      */
      //Vérification si l'utilisateur visé par la requete est le même que l'utilisateur actuellement authentifié
            case "User":
                if (currentUser.getId() == entityId) {
                    authorized = true;
                }
                break;
            default :
                authorized = false;
                break;
        }

        return authorized;

    }

}

