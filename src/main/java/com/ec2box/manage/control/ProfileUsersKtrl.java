/**
 *    Copyright (C) 2015 Loophole, LLC
 *
 *    This program is free software: you can redistribute it and/or  modify
 *    it under the terms of the GNU Affero General Public License, version 3,
 *    as published by the Free Software Foundation.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *    As a special exception, the copyright holders give permission to link the
 *    code of portions of this program with the OpenSSL library under certain
 *    conditions as described in each individual source file and distribute
 *    linked combinations including the program with the OpenSSL library. You
 *    must comply with the GNU Affero General Public License in all respects for
 *    all of the code used other than as permitted herein. If you modify file(s)
 *    with this exception, you may extend this exception to your version of the
 *    file(s), but you are not obligated to do so. If you do not wish to do so,
 *    delete this exception statement from your version. If you delete this
 *    exception statement from all source files in the program, then also delete
 *    it in the license file.
 */
package com.ec2box.manage.control;

import com.ec2box.manage.db.ProfileDB;
import com.ec2box.manage.db.UserDB;
import com.ec2box.manage.db.UserProfileDB;
import com.ec2box.manage.model.Profile;
import com.ec2box.manage.model.SortedSet;
import loophole.mvc.annotation.Kontrol;
import loophole.mvc.annotation.MethodType;
import loophole.mvc.annotation.Model;
import loophole.mvc.base.BaseKontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Action to assign users to profiles
 */
public class ProfileUsersKtrl extends BaseKontroller {


    @Model(name = "profile")
    Profile profile;
    @Model(name = "sortedSet")
    SortedSet sortedSet = new SortedSet();
    @Model(name = "userSelectId")
    List<Long> userSelectId = new ArrayList<>();

    public ProfileUsersKtrl(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Kontrol(path = "/manage/viewProfileUsers", method = MethodType.GET)
    public String viewProfileUsers() {
        if (profile != null && profile.getId() != null) {
            profile = ProfileDB.getProfile(profile.getId());
            sortedSet = UserDB.getAdminUserSet(sortedSet, profile.getId());
        }
        return "/manage/view_profile_users.html";
    }

    @Kontrol(path = "/manage/assignUsersToProfile", method = MethodType.POST)
    public String assignSystemsToProfile() {

        if (userSelectId != null) {
            UserProfileDB.setUsersForProfile(profile.getId(), userSelectId);
        }
        return "redirect:/manage/viewProfiles.ktrl";
    }

}
