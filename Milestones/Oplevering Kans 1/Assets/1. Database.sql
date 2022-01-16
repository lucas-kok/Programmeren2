CREATE DATABASE CodeCademyDB

USE [CodeCademyDB]
GO
/****** Object:  Table [dbo].[Certificate]    Script Date: 1/13/2022 7:52:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Certificate](
	[CertificateID] [int] IDENTITY(1,1) NOT NULL,
	[RegisterID] [int] NOT NULL,
	[Score] [int] NULL,
	[StaffName] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[CertificateID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ContentItem]    Script Date: 1/13/2022 7:52:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ContentItem](
	[ContentID] [int] IDENTITY(1,1) NOT NULL,
	[CourseName] [nvarchar](50) NULL,
	[Title] [nvarchar](50) NULL,
	[PublicationDate] [date] NULL,
	[Status] [nvarchar](50) NULL,
	[FollowNumber] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ContentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Course]    Script Date: 1/13/2022 7:52:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Course](
	[Coursename] [nvarchar](50) NOT NULL,
	[Subject] [nvarchar](50) NULL,
	[IntroductionText] [nvarchar](50) NULL,
	[Level] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[Coursename] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Module]    Script Date: 1/13/2022 7:52:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Module](
	[ContentID] [int] NOT NULL,
	[Version] [nvarchar](50) NULL,
	[Description] [nvarchar](50) NULL,
	[ContactPersonName] [nvarchar](50) NULL,
	[ContactPersonEmail] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[ContentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Register]    Script Date: 1/13/2022 7:52:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Register](
	[RegisterID] [int] IDENTITY(1,1) NOT NULL,
	[Registerdate] [date] NULL,
	[StudentEmail] [nvarchar](50) NOT NULL,
	[CourseName] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[RegisterID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RelatedCourses]    Script Date: 1/13/2022 7:52:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RelatedCourses](
	[CourseOneName] [nvarchar](50) NULL,
	[CourseTwoName] [nvarchar](50) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Student]    Script Date: 1/13/2022 7:52:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Student](
	[Email] [nvarchar](50) NOT NULL,
	[Name] [nvarchar](50) NULL,
	[Birthday] [date] NULL,
	[Gender] [char](1) NULL,
	[Address] [nvarchar](50) NULL,
	[City] [nvarchar](50) NULL,
	[Country] [nvarchar](50) NULL,
	[PostalCode] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Viewing]    Script Date: 1/13/2022 7:52:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Viewing](
	[StudentEmail] [nvarchar](50) NOT NULL,
	[ContenID] [int] NOT NULL,
	[Progress] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Webcast]    Script Date: 1/13/2022 7:52:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Webcast](
	[ContentID] [int] NOT NULL,
	[Duration] [int] NULL,
	[URL] [nvarchar](50) NULL,
	[SpeakerName] [nvarchar](50) NULL,
	[SpeakerCompany] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[ContentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Certificate]  WITH CHECK ADD  CONSTRAINT [FK__Certifica__Regis__2C3393D0] FOREIGN KEY([RegisterID])
REFERENCES [dbo].[Register] ([RegisterID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Certificate] CHECK CONSTRAINT [FK__Certifica__Regis__2C3393D0]
GO
ALTER TABLE [dbo].[ContentItem]  WITH CHECK ADD  CONSTRAINT [FK__ContentIt__Cours__2F10007B] FOREIGN KEY([CourseName])
REFERENCES [dbo].[Course] ([Coursename])
ON UPDATE CASCADE
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[ContentItem] CHECK CONSTRAINT [FK__ContentIt__Cours__2F10007B]
GO
ALTER TABLE [dbo].[Module]  WITH CHECK ADD FOREIGN KEY([ContentID])
REFERENCES [dbo].[ContentItem] ([ContentID])
GO
ALTER TABLE [dbo].[Register]  WITH CHECK ADD  CONSTRAINT [FK__Register__Course__29572725] FOREIGN KEY([CourseName])
REFERENCES [dbo].[Course] ([Coursename])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Register] CHECK CONSTRAINT [FK__Register__Course__29572725]
GO
ALTER TABLE [dbo].[Register]  WITH CHECK ADD  CONSTRAINT [FK__Register__Studen__286302EC] FOREIGN KEY([StudentEmail])
REFERENCES [dbo].[Student] ([Email])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Register] CHECK CONSTRAINT [FK__Register__Studen__286302EC]
GO
ALTER TABLE [dbo].[RelatedCourses]  WITH CHECK ADD  CONSTRAINT [FK__RelatedCo__Cours__534D60F1] FOREIGN KEY([CourseOneName])
REFERENCES [dbo].[Course] ([Coursename])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[RelatedCourses] CHECK CONSTRAINT [FK__RelatedCo__Cours__534D60F1]
GO
ALTER TABLE [dbo].[Viewing]  WITH CHECK ADD FOREIGN KEY([ContenID])
REFERENCES [dbo].[ContentItem] ([ContentID])
GO
ALTER TABLE [dbo].[Viewing]  WITH CHECK ADD  CONSTRAINT [FK__Viewing__Student__30F848ED] FOREIGN KEY([StudentEmail])
REFERENCES [dbo].[Student] ([Email])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Viewing] CHECK CONSTRAINT [FK__Viewing__Student__30F848ED]
GO
ALTER TABLE [dbo].[Webcast]  WITH CHECK ADD FOREIGN KEY([ContentID])
REFERENCES [dbo].[ContentItem] ([ContentID])
GO


--BEGIN TRAN

INSERT INTO dbo.Student VALUES
	 ( 'lucas.kok@hotmail.nl', 'Lucas Kok', CONVERT(datetime, '2005/01/09'), 'm', 'Perenmeet 48', 'Burgh-Haamstede', 'The Netherlands', '4328 CM' )
	 ,( 'ditabultman@teleworm.us', 'Dita Bultman', CONVERT(datetime, '1984/10/08'), 'f', 'Waterleliestraat 152', 'Almere', 'The Netherlands', '1338 TK' )
	 ,( 'kovanderbiezen@dayrep.com', 'Ko Van Der Biezen', CONVERT(datetime, '1962/09/30'), 'm', 'Sint LiduinaStraat 160', 'Schiedam', 'The Netherlands', '3117CV' )
	 ,( 'drieswiersma@dayrep.org', 'Dries Wiersma', CONVERT(datetime, '1995/02/14'), 'm', 'Du Meelaan 124', 'Zoetermeer', 'The Netherlands', '2722 ZC' )
	 ,( 'randolphelders@gmail.com', 'Randolph Elders', CONVERT(datetime, '1983/11/27'), 'm', 'Sweelinckstraat 56', 'Nijverdal', 'The Netherlands', '7442 JS' )
	 ,( 'warrengoede@hotmail.com', 'Warren Goede', CONVERT(datetime, '1998/04/10'), 'm', 'Prins Bernhardlaan 196', 'Voorburg', 'The Netherlands', '2274 JC' )
	 ,( 'jelcorodermond@outlook.com', 'Jelco Rodermond', CONVERT(datetime, '1939/09/10'), 'm', 'Oranjelaan 129', 'Groenekan', 'The Netherlands', '3737 AS' )
	 ,( 'martikastuit@student.avans.nl', 'Martika Stuit', CONVERT(datetime, '1999/02/01'), 'f', 'Amperestraat 56', 'Alblasserdam', 'The Netherlands', '2952 AA' )
	 ,( 'pascallewildschut@rhyta.com', 'Pascalle Wildschut', CONVERT(datetime, '1996/04/17'), 'x', 'Herenstraat 73', 'Amersfoort', 'The Netherlands', '3811 HG' )
	 ,( 'mouradhuberts@gmail.com', 'Mourad Huberts', CONVERT(datetime, '1985/02/08'), 'm', 'Magnolialaan 65', 'Doesburg', 'The Netherlands', '6982 DR' )
	 ,( 'charellevanderholst@armyspy.com', 'Charelle Van Der Horst', CONVERT(datetime, '1984/06/20'), 'f', 'Schuilenburg 178', 'Hoofddorp', 'The Netherlands', '2135 GN' )
	 ,( 'diyar.van.dartel@hotmail.nl', 'Diyar Van Dartel', CONVERT(datetime, '2002/04/11'), 'x', 'Lindeboomsweg 129', 'Lochem', 'The Netherlands', '7241 PR' )
	 ,( 'iepek.gieles53@pontes.nl', 'Ipek Gielens', CONVERT(datetime, '2000/10/06'), 'm', 'Hoge Valkseweg 90', 'Barneveld', 'The Netherlands', '3771 SL' )
	 ,( 'jarondevree@jourrapide.com', 'Jaron De Vree', CONVERT(datetime, '1998/06/07'), 'm', 'Franseweg 103', 'Steenbergen', 'The Netherlands', '4651 PV' )
	 ,( 'tiddo.vd.kaanden9@jourrapide.com', 'Tiddo Van Der Kaanden', CONVERT(datetime, '2004/09/01'), 'm', 'Edisonstraat 33', 'Leeuwarden', 'The Netherlands', '8912 AW' )
	 ,( 'youknowmeluca@gmail.com', 'Luca Wiskerke', CONVERT(datetime, '1991/04/14'), 'm', 'Van Coothstraat 196', 'Boxtel', 'The Netherlands', '5281 CV' )
	 ,( 'guineverebult9@pokemon.nl', 'Guinevere Bult', CONVERT(datetime, '1969/02/11'), 'f', 'Kooweide 108', 'Eibergen', 'The Netherlands', '7152 KH' )
	 ,( 'horselover21@hives.com', 'Sydney Van Der Ben', CONVERT(datetime, '2003/05/17'), 'o', 'Amstelkade 9', 'Amstelhoek', 'The Netherlands', '1427 AM' )
	 ,( 'k.schevers@hotmail.nl', 'Kicky Schevers', CONVERT(datetime, '1976/11/10'), 'f', 'De Terrassen 13', 'Apeldoorn', 'The Netherlands', '7325 JD' )
	 ,( 'peter.kok@dekra.com', 'Peter Kok', CONVERT(datetime, '1950/06/10'), 'm', 'Perenmeet 48', 'Burgh-Haamstede', 'The Netherlands', '4328CM' )
	 ,( 'aimane.g@email.net', 'Aïmane Gierveld', CONVERT(datetime, '2004/03/04'), 'm', 'Kempkensweg 144', 'Heerlen', 'The Netherlands', '6412 AV' )
	 ,( 'homeemail.liese@jourrapide.com', 'Liese Hoegen', CONVERT(datetime, '1983/06/22'), 'f', 'Dirk Fockstraat 56', 'Duurstede', 'The Netherlands', '3961 DN' )
	 ,( 'malika.verhorst@armyspy.com', 'Malika Verhorst', CONVERT(datetime, '1997/04/01'), 'f', 'Okkistraat 118', 'Almere', 'The Netherlands', '1136 KT' )
	 ,( 'm.rauwerda@pokemon.nl', 'Michel Rauwerda', CONVERT(datetime, '1985/05/22'), 'm', 'Brasiliastraat 102', 'Den Haag', 'The Netherlands', '2548 GX' )
	 ,( 'daan.vd.meulen@student.avans.nl', 'Daan Van Der Meulen', CONVERT(datetime, '2002/12/31'), 'o', 'Boslaan 185', 'Emmeloord', 'The Netherlands', '8302 AB' )
	 ,( 'renzo.remmers@student.avans.nl', 'Renzo Remmers', CONVERT(datetime, '2000/01/01'), 'f', 'Ybenhaer 16', 'Oosterwolde', 'The Netherlands', '8431 HD' )
	 ,( 'anne.vandenbosch57@student.avans.nl', 'Anne Van Den Bosch', CONVERT(datetime, '1990/02/25'), 'f', 'Jouwsmastraat 192', 'Leeuwarden', 'The Netherlands', '9834 AX' )
	 ,( 'arno.broeders@docent.avans.nl', 'Arno Broeders', CONVERT(datetime, '1976/05/10'), 'm', 'Stortstraat 150', 'Schoonoord', 'The Netherlands', '7848 CB' )
	 ,( 'bradleyuitendaal@dayrep.com', 'Bradley Uitendaal', CONVERT(datetime, ''), 'o', 'Van Der Waalslaan 124', 'Almelo', 'The Netherlands', '7603 MP' )
	 ,( 'alexander.vd.bulck@docent.avans.nl', 'Alexander Van Den Bulck', CONVERT(datetime, '1992/11/23'), 'm', 'Pelikaanstraat 115', 'Veenendaal', 'The Netherlands', '3903 AG' );

INSERT INTO Course VALUES
	('Programmeren 1', 'Programmeren', 'Welkom bij programmeren.', 'Beginner')
	,('Relationele Databases 1', 'SQL Server Databases', 'Welkom bij Relationele Databases.', 'Beginner')
	,('Bedrijfsprocessen', 'Bedrijfsprocessen', 'Welkom bij bedrijfsprocessen.', 'Beginner')
	,('Programmeren 2', 'Programmeren', 'Welkom bij programmeren 2.', 'Advanced')
	,('Relationele Databases 2', 'Database ontwerp', 'Welkom bij Relationele Databases 2.', 'Advanced')
	,('Artificial Intelligence', 'Artificial Intelligence', 'Welkom bij Artificial Intelligence.', 'Expert')
	,('Cyber Security', 'Cyber Security', 'Welkom bij Cyber Security.', 'Expert')

INSERT INTO ContentItem VALUES
	('Programmeren 1', 'Les 1 Programmeren', GETDATE(), 'Active', (SELECT ContentId FROM ContentItem WHERE Title = 'Les 2 Programmeren'))
	,('Programmeren 1', 'Les 2 Programmeren',  GETDATE(), 'Active', (SELECT ContentId FROM ContentItem WHERE Title = 'Les 3 Programmeren'))
	,('Programmeren 1', 'Les 3 Programmeren',  GETDATE(), 'Active', NULL)
	,('Programmeren 1', 'MOOC Programmeren 1',  GETDATE(), 'Active', NULL)
	,('Relationele Databases 1', 'Les 1 Relationele Databases',  GETDATE(), 'Active', (SELECT ContentId FROM ContentItem WHERE Title = 'Les 2 Programmeren'))
	,('Relationele Databases 1', 'Les 2 Relationele Databases',  GETDATE(), 'Active', NULL)
	,('Relationele Databases 1', 'SQL Beginners Module',  GETDATE(), 'Active', NULL)
	,('Bedrijfsprocessen', 'Les 1 Bedrijfsprocessen', GETDATE(), 'Active', (SELECT ContentId FROM ContentItem WHERE Title = 'Les 2 Bedrijfsprocessen'))
	,('Bedrijfsprocessen', 'Les 2 Bedrijfsprocessen',  GETDATE(), 'Active', (SELECT ContentId FROM ContentItem WHERE Title = 'Les 3 Bedrijfsprocessen'))
	,('Bedrijfsprocessen', 'Les 3 Bedrijfsprocessen',  GETDATE(), 'Active', NULL)
	,('Programmeren 2', 'Les 1 Programmeren 2',  GETDATE(), 'Active', (SELECT ContentId FROM ContentItem WHERE Title = 'Les 2 Programmeren 2'))
	,('Programmeren 2', 'Les 2 Programmeren 2',  GETDATE(), 'Active', NULL)
	,('Relationele Databases 2', 'Les 1 Relationele Databases 2',  GETDATE(), 'Active', (SELECT ContentId FROM ContentItem WHERE Title = 'Les 2 Relationele Databases 2'))
	,('Relationele Databases 2', 'Les 2 Relationele Databases 2',  GETDATE(), 'Active', NULL)
	,('Artificial Intelligence','Artificial Intelligence Beginning', GETDATE(), 'Active', NULL)
	,('Artificial Intelligence','Artificial Intelligence Advanced', GETDATE(), 'Active', NULL)
	,('Artificial Intelligence','Artificial Intelligence Expert', GETDATE(), 'Active', NULL)
	,('Cyber Security','Cyber Security deel 1', GETDATE(), 'Active', NULL)
	,('Cyber Security','Cyber Security deel 2', GETDATE(), 'Active', NULL)
	,(NULL,'Python deel 1', GETDATE(), 'Active', NULL)
	,(NULL,'Python deel 2', GETDATE(), 'Active', NULL)
	,(NULL,'C# deel 1', GETDATE(), 'Active', NULL)
	,(NULL,'C# deel 2', GETDATE(), 'Active', NULL)

INSERT INTO Webcast VALUES
	((SELECT ContentId FROM ContentItem WHERE Title = 'Les 1 Programmeren'),30,'https://www.youtube.com/Les1Programmeren', 'Arno Broeders', 'Avans Hogeschool Breda')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Les 2 Programmeren'),40,'https://www.youtube.com/Les2Programmeren', 'Arno Broeders', 'Avans Hogeschool Breda')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Les 3 Programmeren'),34,'https://www.youtube.com/Les3Programmeren', 'Arno Broeders', 'Avans Hogeschool Breda')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Les 1 Relationele Databases'),50,'https://www.youtube.com/Les1RelationeleDatabases', 'Pascal van Gastel', 'Avans Hogeschool Breda')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Les 2 Relationele Databases'),35,'https://www.youtube.com/Les2RelationeleDatabases', 'Pascal van Gastel', 'Avans Hogeschool Breda')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Les 1 Bedrijfsprocessen'),65,'https://www.youtube.com/Les1Bedrijfsprocessen', 'Alexander Van Den Bulck', 'Avans Hogeschool Breda')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Les 2 Bedrijfsprocessen'),65,'https://www.youtube.com/Les2Bedrijfsprocessen', 'Alexander Van Den Bulck', 'Avans Hogeschool Breda')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Les 3 Bedrijfsprocessen'),65,'https://www.youtube.com/Les3Bedrijfsprocessen', 'Alexander Van Den Bulck', 'Avans Hogeschool Breda')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Les 1 Programmeren 2'),30,'https://www.youtube.com/Les1Programmeren2', 'Arno Broeders', 'Avans Hogeschool Breda')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Les 2 Programmeren 2'),40,'https://www.youtube.com/Les2Programmeren2', 'Arno Broeders', 'Avans Hogeschool Breda')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Les 1 Relationele Databases 2'),50,'https://www.youtube.com/Les1RelationeleDatabases2', 'Pascal van Gastel', 'Avans Hogeschool Breda')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Les 2 Relationele Databases 2'),35,'https://www.youtube.com/Les2RelationeleDatabases2', 'Pascal van Gastel', 'Avans Hogeschool Breda')

INSERT INTO Module VALUES
	((SELECT ContentId FROM ContentItem WHERE Title = 'MOOC Programmeren 1'),'2.0','MOOC Programmeren 1', 'Arno Broeders', 'arno.broeders@docent.avans.nl')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'SQL Beginners Module'),'2.4','SQL Beginners Module', 'Pascal van Gastel', 'pascalvgastel@docent.avans.nl')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Artificial Intelligence Beginning'),'3.5','Artificial Intelligence Beginning','Henri Kluit','henri.kluit@tudelft.nl')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Artificial Intelligence Advanced'),'3.5','Artificial Intelligence Advanced','Henri Kluit','henri.kluit@tudelft.nl')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Artificial Intelligence Expert'),'3.5','Artificial Intelligence Expert','Henri Kluit','henri.kluit@tudelft.nl')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Cyber Security deel 1'),'3.5','Cyber Security deel 1','Dirk-Jan Lommers','djl@tudelft.nl')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Cyber Security deel 2'),'3.5','Cyber Security deel 2','Dirk-Jan Lommers','djl@tudelft.nl')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Python deel 1'),'3.5','Python deel 1','Dirk-Jan Lommers','djl@tudelft.nl')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'Python deel 2'),'3.5','Python deel 1','Dirk-Jan Lommers','djl@tudelft.nl')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'C# deel 1'),'3.5','C# deel 1','Henri Kluit','henri.kluit@tudelft.nl')
	,((SELECT ContentId FROM ContentItem WHERE Title = 'C# deel 2'),'3.5','C# deel 2','Henri Kluit','henri.kluit@tudelft.nl')

INSERT INTO Register VALUES
	(getdate(),'lucas.kok@hotmail.nl','Programmeren 1')
	,(getdate(),'ditabultman@teleworm.us','Programmeren 1')
	,(getdate(),'kovanderbiezen@dayrep.com','Programmeren 1')
	,(getdate(),'drieswiersma@dayrep.org','Programmeren 1')
	,(getdate(),'randolphelders@gmail.com','Programmeren 1')
	,(getdate(),'warrengoede@hotmail.com','Programmeren 1')
	,(getdate(),'jelcorodermond@outlook.com','Programmeren 1')
	,(getdate(),'martikastuit@student.avans.nl','Programmeren 1')
	,(getdate(),'pascallewildschut@rhyta.com','Programmeren 1')
	,(getdate(),'mouradhuberts@gmail.com','Programmeren 1')

	,(getdate(),'lucas.kok@hotmail.nl','Relationele Databases 1')
	,(getdate(),'ditabultman@teleworm.us','Relationele Databases 1')
	,(getdate(),'kovanderbiezen@dayrep.com','Relationele Databases 1')
	,(getdate(),'drieswiersma@dayrep.org','Relationele Databases 1')
	,(getdate(),'warrengoede@hotmail.com','Relationele Databases 1')
	,(getdate(),'jelcorodermond@outlook.com','Relationele Databases 1')
	,(getdate(),'martikastuit@student.avans.nl','Relationele Databases 1')
	,(getdate(),'mouradhuberts@gmail.com','Relationele Databases 1')

	,(getdate(),'lucas.kok@hotmail.nl','Bedrijfsprocessen')
	,(getdate(),'kovanderbiezen@dayrep.com','Bedrijfsprocessen')
	,(getdate(),'drieswiersma@dayrep.org','Bedrijfsprocessen')
	,(getdate(),'jelcorodermond@outlook.com','Bedrijfsprocessen')
	,(getdate(),'martikastuit@student.avans.nl','Bedrijfsprocessen')
	,(getdate(),'pascallewildschut@rhyta.com','Bedrijfsprocessen')

	,(getdate(),'randolphelders@gmail.com','Programmeren 2')
	,(getdate(),'warrengoede@hotmail.com','Programmeren 2')
	,(getdate(),'jelcorodermond@outlook.com','Programmeren 2')
	,(getdate(),'martikastuit@student.avans.nl','Programmeren 2')

	,(getdate(),'ditabultman@teleworm.us','Relationele Databases 2')
	,(getdate(),'kovanderbiezen@dayrep.com','Relationele Databases 2')
	,(getdate(),'drieswiersma@dayrep.org','Relationele Databases 2')
	,(getdate(),'randolphelders@gmail.com','Relationele Databases 2')
	,(getdate(),'warrengoede@hotmail.com','Relationele Databases 2')
	,(getdate(),'jelcorodermond@outlook.com','Relationele Databases 2')

	,(getdate(),'drieswiersma@dayrep.org','Artificial Intelligence')
	,(getdate(),'randolphelders@gmail.com','Artificial Intelligence')
	,(getdate(),'warrengoede@hotmail.com','Artificial Intelligence')

	,(getdate(),'martikastuit@student.avans.nl','Cyber Security')
	,(getdate(),'pascallewildschut@rhyta.com','Cyber Security')
	,(getdate(),'mouradhuberts@gmail.com','Cyber Security')

INSERT INTO Viewing VALUES 
	('lucas.kok@hotmail.nl', 1, 45)
	,('lucas.kok@hotmail.nl', 2, 0)
	,('lucas.kok@hotmail.nl', 3, 0)
	,('lucas.kok@hotmail.nl', 4, 0)
	,('ditabultman@teleworm.us', 1, 85)
	,('ditabultman@teleworm.us', 2, 0)
	,('ditabultman@teleworm.us', 3, 0)
	,('ditabultman@teleworm.us', 4, 0)
	,('kovanderbiezen@dayrep.com', 1, 100)
	,('kovanderbiezen@dayrep.com', 2, 52)
	,('kovanderbiezen@dayrep.com', 3, 0)
	,('kovanderbiezen@dayrep.com', 4, 0)
	,('drieswiersma@dayrep.org', 1, 100)
	,('drieswiersma@dayrep.org', 2, 100)
	,('drieswiersma@dayrep.org', 3, 100)
	,('drieswiersma@dayrep.org', 4, 100)
	,('randolphelders@gmail.com', 1, 100)
	,('randolphelders@gmail.com', 2, 100)
	,('randolphelders@gmail.com', 3, 25)
	,('randolphelders@gmail.com', 4, 0)
	,('warrengoede@hotmail.com', 1, 100)
	,('warrengoede@hotmail.com', 2, 99)
	,('warrengoede@hotmail.com', 3, 0)
	,('warrengoede@hotmail.com', 4, 0)
	,('jelcorodermond@outlook.com', 1, 10)
	,('jelcorodermond@outlook.com', 2, 0)
	,('jelcorodermond@outlook.com', 3, 0)
	,('jelcorodermond@outlook.com', 4, 0)
	,('martikastuit@student.avans.nl', 1, 100)
	,('martikastuit@student.avans.nl', 2, 100)
	,('martikastuit@student.avans.nl', 3, 37)
	,('martikastuit@student.avans.nl', 4, 0)
	,('pascallewildschut@rhyta.com', 1, 58)
	,('pascallewildschut@rhyta.com', 2, 0)
	,('pascallewildschut@rhyta.com', 3, 0)
	,('pascallewildschut@rhyta.com', 4, 0)
	,('mouradhuberts@gmail.com', 1, 100)
	,('mouradhuberts@gmail.com', 2, 63)
	,('mouradhuberts@gmail.com', 3, 0)
	,('mouradhuberts@gmail.com', 4, 0)

	,('lucas.kok@hotmail.nl', 5, 100)
	,('lucas.kok@hotmail.nl', 6, 100)
	,('lucas.kok@hotmail.nl', 7, 100)
	,('ditabultman@teleworm.us', 5, 100)
	,('ditabultman@teleworm.us', 6, 55)
	,('ditabultman@teleworm.us', 7, 0 )
	,('kovanderbiezen@dayrep.com', 5, 26)
	,('kovanderbiezen@dayrep.com', 6, 0)
	,('kovanderbiezen@dayrep.com', 7, 0 )
	,('drieswiersma@dayrep.org', 5, 100)
	,('drieswiersma@dayrep.org', 6, 85)
	,('drieswiersma@dayrep.org', 7, 0 )
	,('warrengoede@hotmail.com', 5, 68)
	,('warrengoede@hotmail.com', 6, 0)
	,('warrengoede@hotmail.com', 7, 0 )
	,('jelcorodermond@outlook.com', 5, 100)
	,('jelcorodermond@outlook.com', 6, 100)
	,('jelcorodermond@outlook.com', 7, 59)
	,('martikastuit@student.avans.nl', 5, 89)
	,('martikastuit@student.avans.nl', 6, 0)
	,('martikastuit@student.avans.nl', 7, 0)
	,('mouradhuberts@gmail.com', 5, 100)
	,('mouradhuberts@gmail.com', 6, 100)
	,('mouradhuberts@gmail.com', 7, 100)

	,('lucas.kok@hotmail.nl', 8, 100)
	,('lucas.kok@hotmail.nl', 9, 100)
	,('lucas.kok@hotmail.nl', 10, 2)
	,('kovanderbiezen@dayrep.com', 8, 12)
	,('kovanderbiezen@dayrep.com', 9, 0)
	,('kovanderbiezen@dayrep.com', 10, 0)
	,('drieswiersma@dayrep.org', 8, 98)
	,('drieswiersma@dayrep.org', 9, 0)
	,('drieswiersma@dayrep.org', 10, 0)
	,('jelcorodermond@outlook.com', 8, 100)
	,('jelcorodermond@outlook.com', 9, 100)
	,('jelcorodermond@outlook.com', 10, 100)
	,('martikastuit@student.avans.nl', 8, 100)
	,('martikastuit@student.avans.nl', 9, 35)
	,('martikastuit@student.avans.nl', 10, 0)
	,('pascallewildschut@rhyta.com', 8, 10)
	,('pascallewildschut@rhyta.com', 9, 0)
	,('pascallewildschut@rhyta.com', 10, 0)

	,('randolphelders@gmail.com', 11, 100)
	,('randolphelders@gmail.com', 12, 100)
	,('warrengoede@hotmail.com', 11, 100)
	,('warrengoede@hotmail.com', 12, 87)
	,('jelcorodermond@outlook.com', 11, 74)
	,('jelcorodermond@outlook.com', 12, 0)
	,('martikastuit@student.avans.nl', 11, 52)
	,('martikastuit@student.avans.nl', 12, 0)

	,('ditabultman@teleworm.us', 13, 58)
	,('ditabultman@teleworm.us', 14, 0)
	,('kovanderbiezen@dayrep.com', 13, 100)
	,('kovanderbiezen@dayrep.com', 14, 41)
	,('drieswiersma@dayrep.org', 13, 98)
	,('drieswiersma@dayrep.org', 14, 0)
	,('randolphelders@gmail.com', 13, 100)
	,('randolphelders@gmail.com', 14, 28)
	,('warrengoede@hotmail.com', 13, 100)
	,('warrengoede@hotmail.com', 14, 78)
	,('jelcorodermond@outlook.com', 13, 100)
	,('jelcorodermond@outlook.com', 14, 100)

	,('drieswiersma@dayrep.org', 15, 100)
	,('drieswiersma@dayrep.org', 16, 100)
	,('drieswiersma@dayrep.org', 17, 100)
	,('randolphelders@gmail.com', 15, 100)
	,('randolphelders@gmail.com', 16, 42)
	,('randolphelders@gmail.com', 17, 0)
	,('warrengoede@hotmail.com', 15, 100)
	,('warrengoede@hotmail.com', 16, 65)
	,('warrengoede@hotmail.com', 17, 0)

	,('martikastuit@student.avans.nl', 18, 67)
	,('martikastuit@student.avans.nl', 19, 0)
	,('pascallewildschut@rhyta.com', 18, 100)
	,('pascallewildschut@rhyta.com', 19, 25)
	,('mouradhuberts@gmail.com', 18, 20)
	,('mouradhuberts@gmail.com', 19, 0);

INSERT INTO Certificate VALUES
	(4, 8, 'Fred Van Gardingen')
	,(11, 9, 'Yascino Hubbers')
	,(18, 10, 'Fred van Gardigen')
	,(16, 6, 'Yascino Hubbers')
	,(25, 10, 'Ayrianna Schot')
	,(34, 6, 'Ronald Pols')
	,(35, 10, 'Ronald Pols');
--ROLLBACK
