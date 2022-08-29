export interface Comment {
    id: number,
    content: string,
    createdAt: Date,
    lastModificationAt: Date,
    userID: number,
    postID: number,
    parentID: number | null,
    children: Comment[],
}