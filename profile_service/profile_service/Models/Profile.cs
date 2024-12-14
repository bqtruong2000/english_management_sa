using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace profile_service.Models
{
    public class Profile
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string _id { get; set; }

        [BsonElement("code")]
        public string Code { get; set; }

        [BsonElement("firstName")]
        public string FirstName { get; set; }

        [BsonElement("lastName")]
        public string LastName { get; set; }

        [BsonElement("dob")]
        public string Dob { get; set; }

        [BsonElement("email")]
        public string Email { get; set; }

        [BsonElement("phoneNum")]
        public string PhoneNum { get; set; }

        [BsonElement("avatar")]
        public string Avatar { get; set; }

        [BsonElement("_class")]
        public string _class { get; set; }
    }
}
