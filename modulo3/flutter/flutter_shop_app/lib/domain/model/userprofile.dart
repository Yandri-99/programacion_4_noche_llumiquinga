class UserProfile {
  final int id;
  final String username;
  final String email;
  final String firstName;
  final String lastName;
  final String? avatarUrl;
  final bool isStaff;
  final bool isActive;
  final String dateJoined;
  final int numOrders;

  const UserProfile({
    required this.id,
    required this.username,
    required this.email,
    required this.firstName,
    required this.lastName,
    this.avatarUrl,
    required this.isStaff,
    required this.isActive,
    required this.dateJoined,
    required this.numOrders,
  });

  factory UserProfile.fromJson(Map<String, dynamic> j) => UserProfile(
    id:         j['id']          as int,
    username:   j['username']    as String,
    email:      j['email']       as String,
    firstName:  j['first_name']  as String,
    lastName:   j['last_name']   as String,
    avatarUrl:  j['avatar']     as String?,
    isStaff:    j['is_staff']    as bool,
    isActive:   j['is_active']   as bool,
    dateJoined: j['date_joined'] as String,
    numOrders:  j['num_orders']  as int,
  );
}
