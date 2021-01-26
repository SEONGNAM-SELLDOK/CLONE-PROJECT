package com.selldok.toy.employee.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**  * FaceBookFriendResult
 *
 * @author incheol.jung
 * @since 2021. 01. 01.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FaceBookFriendResult {
	List<FaceBookFriend> data;
}
