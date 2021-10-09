/*============================== INSERT DATABASE =======================================*/
/*======================================================================================*/
-- Add data `Group`
INSERT INTO `Group`	( `GroupID`	, `GroupName`		, `MemberNum`	, `CreatorID`	, `CreateDate`			,`ModifiedDate`) 
VALUES				(     1		, 'Marketing'		, 2				,  1			, '2021-09-28 20:21:13'	,'2021-09-28 20:21:13'),
					(     2		, 'Development'		, 0				,  1			, '2021-10-02 20:21:24'	,'2021-10-02 20:21:24'),
					(     3		, 'Creator'			, 1				,  1			, '2021-09-28 20:21:38'	,'2021-09-28 20:21:38'),
					(     4		, 'Unlimited'		, 5				,  2			, '2021-09-28 20:21:44'	,'2021-09-28 20:21:44'),
					(     5		, 'Management'		, 0				,  1			, '2021-05-20 20:21:52'	,'2021-05-20 20:21:52'),
                    (     6		, 'The Flash'		, 0				,  1			, '2021-09-28 20:21:38'	,'2021-09-28 20:21:38'),
					(     7		, 'Avanger'			, 0				,  2			, '2021-09-28 20:21:44'	,'2021-09-28 20:21:44'),
					(     8		, 'Super Hero'		, 0				,  1			, '2021-09-28 20:21:52'	,'2021-09-28 20:21:52');

-- Add data Account
-- password: 123456
INSERT INTO `Account`	(`AccountID`, `Email`				, `Username`	, `password`													, `FirstName`	, `LastName`	, `GroupID`	,`Role`		, `CreateDate`			) 
VALUES					(    1		, 'trantuan@gmail.com'	, 'trantuan01'	, '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi', 'Tuan'		, 'Tran Anh'	,   NULL	,'Admin'	, '2021-09-28 20:16:40'	),
						(    2		, 'elonmusk@gmail.com'	, 'elonmusk'	, '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi', 'Elon'		, 'Musk'		,   NULL	, 'Admin'	,'2021-09-28 20:17:32'	),
						(    3		, 'onyxzt123@gmail.com'	, 'onyxzt123'	, '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi', 'Truong'		, 'Bui Duc'		,   NULL	, 'User'	,'2021-04-28 20:18:06'	),
						(    4		, 'thanglong98@gmail.com', 'thanglong98', '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi', 'Long'		, 'Tran Thang'	,   NULL	, 'Manager'	,'2021-05-28 20:18:43'	),
						(    5		, 'va29042000@gmail.com', 'va29042000'	, '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi', 'Anh'			, 'Truong Viet'	,   NULL	, 'User'	,'2021-06-28 20:19:36'	),
						(    6		, 'NPHang@gmail.com'	, 'nphang'		, '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi', 'Hang'		, 'Nguyen Phuong',  NULL	, 'User'	,'2021-04-28 20:18:06'	),
						(    7		, 'phamnhatvuong@gmail.com', 'pnvuong'	, '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi', 'Vuong'		, 'Pham Nhat'	,   NULL	, 'User'	,'2021-05-28 20:18:43'	),
						(    8		, 'minhnhua@gmail.com', 'minhnhua'		, '$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi', 'Nhat Minh'	, 'Pham Tran'	,   NULL	, 'User'	,'2021-06-28 20:19:36'	);

ALTER TABLE `Group`
ADD FOREIGN KEY (CreatorID) REFERENCES `Account`(AccountID);