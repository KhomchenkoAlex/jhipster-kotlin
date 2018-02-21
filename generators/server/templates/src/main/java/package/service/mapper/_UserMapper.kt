<%#
 Copyright 2013-2018 the original author or authors from the JHipster project.

 This file is part of the JHipster project, see http://www.jhipster.tech/
 for more information.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-%>
package <%=packageName%>.service.mapper

<%_ if (databaseType === 'sql' || databaseType === 'mongodb') { _%>
import <%=packageName%>.domain.Authority
<%_ } _%>
import <%=packageName%>.domain.User
import <%=packageName%>.service.dto.UserDTO

import org.springframework.stereotype.Service

import java.util.*

/**
 * Mapper for the entity User and its DTO called UserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
class UserMapper {

    fun userToUserDTO(user: User): UserDTO {
        return UserDTO(user)
    }

    fun usersToUserDTOs(users: List<User>): List<UserDTO> {
        return users
            .filter { Objects.nonNull(it) }
            .map{ this.userToUserDTO(it) }
    }

    fun userDTOToUser(userDTO: UserDTO?): User? {
        if (userDTO == null) {
            return null
        } else {
            val user = User()
            user.id = userDTO!!.getId()
            user.login = userDTO!!.getLogin()
            user.firstName = userDTO!!.getFirstName()
            user.lastName = userDTO!!.getLastName()
            user.email = userDTO!!.getEmail()
            <%_ if (databaseType !== 'cassandra') { _%>
            user.imageUrl = userDTO!!.getImageUrl()
            <%_ } _%>
            user.activated = userDTO!!.isActivated()
            user.langKey = userDTO!!.getLangKey()
            <%_ if (databaseType === 'sql' || databaseType === 'mongodb') { _%>
            val authorities = this.authoritiesFromStrings(userDTO!!.getAuthorities())
            if (authorities != null) {
                user.authorities = authorities
            }
            <%_ } _%>
            <%_ if (databaseType === 'cassandra' || databaseType === 'couchbase') { _%>
            user.authorities = userDTO!!.getAuthorities()
            <%_ } _%>
            return user
        }
    }

    fun userDTOsToUsers(userDTOs: List<UserDTO>): List<User?> {
        return userDTOs
            .filter{ Objects.nonNull(it) }
            .map{ this.userDTOToUser(it) }
    }

    fun userFromId(id: <% if (databaseType === 'mongodb' || databaseType === 'couchbase' || databaseType === 'cassandra') { %>String?<% } else { %>Long?<% } %>): User? {
        if (id == null) {
            return null
        }
        val user = User()
        user.id = id
        return user
    }

    <%_ if (databaseType === 'sql' || databaseType === 'mongodb') { _%>

    fun authoritiesFromStrings(strings: Set<String>): Set<Authority>? {
        return strings.map { string ->
            val auth = Authority()
            auth.name = string
            auth
        }.toSet()
    }
    <%_ } _%>
}