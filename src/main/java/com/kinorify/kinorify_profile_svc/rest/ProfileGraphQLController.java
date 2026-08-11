package com.kinorify.kinorify_profile_svc.rest;

import com.kinorify.kinorify_profile_svc.dto.response.ProfileSearchResponseDTO;
import com.kinorify.kinorify_profile_svc.service.ProfileService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Sort;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ProfileGraphQLController {


    private final ProfileService profileService;


    @QueryMapping
    public List<ProfileSearchResponseDTO> searchProfiles(

            @Argument String query,

            @Argument Integer page,

            @Argument Integer size

    ) {


        Pageable pageable =
                PageRequest.of(
                        page == null ? 0 : page,
                        size == null ? 20 : size,
                        Sort.by("displayName").ascending()
                );


        return profileService
                .searchProfiles(
                        query,
                        pageable
                )
                .getContent();

    }

}
