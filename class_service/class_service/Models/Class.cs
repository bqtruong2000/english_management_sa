using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;
using System.Collections.Generic;

namespace class_service.Models
{
    public class Class
    {
        [BsonId]
        [BsonRepresentation(BsonType.String)] // This will ensure that _id is treated as a string
        public string Id { get; set; }

        [BsonElement("className")]
        public string ClassName { get; set; }

        [BsonElement("room")]
        public string Room { get; set; }

        [BsonElement("duration")]
        public string Duration { get; set; }

        [BsonElement("idTeacher")]
        public string IdTeacher { get; set; }

        [BsonElement("listStudent")]
        public List<string> ListStudent { get; set; }

        [BsonElement("_class")]
        public string ClassReference { get; set; }  // Renamed to "ClassReference" for clarity
    }
}
